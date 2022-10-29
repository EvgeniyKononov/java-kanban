package KVServer;

import static java.net.HttpURLConnection.*;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;



public class KVServer {
	public static final int PORT = 8078;
	private static final String INCORRECT_TOKEN_MSG =
			"Запрос неавторизован, нужен параметр в query API_TOKEN со значением апи-ключа";
	private static final String EMPTY_KEY_MSG = "Key для загрузки пустой. key указывается в пути: /load/{key}";
	private static final String EMPTY_VALUE_MSG = "Value для сохранения пустой. value указывается в теле запроса";
	private static final String KEY_MSG = "Значение для ключа ";
	private static final String SAVE_GET_MSG = "/save ждёт GET-запрос, а получил: ";
	private static final String SAVE_POST_MSG = "/save ждёт POST-запрос, а получил: ";
	private static final String REGISTER_GET_MSG = "/register ждёт GET-запрос, а получил ";
	private static final String API_TOKEN_MSG = "API_TOKEN: ";
	private final String apiToken;
	private final HttpServer server;
	private final Map<String, String> data = new HashMap<>();

	public KVServer() throws IOException {
		apiToken = generateApiToken();
		server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
		server.createContext("/register", this::register);
		server.createContext("/save", this::save);
		server.createContext("/load", this::load);
	}

	private void load(HttpExchange h) throws IOException {
		try {
			System.out.println("\n/load");
			if (!hasAuth(h)) {
				System.out.println(INCORRECT_TOKEN_MSG);
				h.sendResponseHeaders(HTTP_FORBIDDEN, 0);
				return;
			}
			if ("GET".equals(h.getRequestMethod())) {
				String key = h.getRequestURI().getPath().substring("/load/".length());
				if (key.isEmpty()) {
					System.out.println(EMPTY_KEY_MSG);
					h.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
					return;
				}
				System.out.println(KEY_MSG + key + " ниже!");
				System.out.println(data.get(key));

				h.sendResponseHeaders(HTTP_OK, 0);
				try (OutputStream os = h.getResponseBody()) {
					os.write(data.get(key).getBytes());
				}
			} else {
				System.out.println(SAVE_GET_MSG + h.getRequestMethod());
				h.sendResponseHeaders(HTTP_BAD_METHOD, 0);
			}
		} finally {
			h.close();
		}
	}

	private void save(HttpExchange h) throws IOException {
		try {
			System.out.println("\n/save");
			if (!hasAuth(h)) {
				System.out.println(INCORRECT_TOKEN_MSG);
				h.sendResponseHeaders(HTTP_FORBIDDEN, 0);
				return;
			}
			if ("POST".equals(h.getRequestMethod())) {
				String key = h.getRequestURI().getPath().substring("/save/".length());
				if (key.isEmpty()) {
					System.out.println(EMPTY_KEY_MSG);
					h.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
					return;
				}
				String value = readText(h);
				if (value.isEmpty()) {
					System.out.println(EMPTY_VALUE_MSG);
					h.sendResponseHeaders(HTTP_BAD_REQUEST, 0);
					return;
				}
				data.put(key, value);
				System.out.println(KEY_MSG + key + " успешно обновлено!");
				h.sendResponseHeaders(HTTP_OK, 0);
			} else {
				System.out.println(SAVE_POST_MSG + h.getRequestMethod());
				h.sendResponseHeaders(HTTP_BAD_METHOD, 0);
			}
		} finally {
			h.close();
		}
	}

	private void register(HttpExchange h) throws IOException {
		try {
			System.out.println("\n/register");
			if ("GET".equals(h.getRequestMethod())) {
				sendText(h, apiToken);
			} else {
				System.out.println(REGISTER_GET_MSG + h.getRequestMethod());
				h.sendResponseHeaders(HTTP_BAD_METHOD, 0);
			}
		} finally {
			h.close();
		}
	}

	public void start() {
		System.out.println("Запускаем сервер на порту " + PORT);
		System.out.println("Открой в браузере http://localhost:" + PORT + "/");
		System.out.println(API_TOKEN_MSG + apiToken);
		server.start();
	}

	public void stop() {
		server.stop(0);
	}

	private String generateApiToken() {
		return "" + System.currentTimeMillis();
	}

	protected boolean hasAuth(HttpExchange h) {
		String rawQuery = h.getRequestURI().getRawQuery();
		return rawQuery != null && (rawQuery.contains("API_TOKEN=" + apiToken) || rawQuery.contains("API_TOKEN=DEBUG"));
	}

	protected String readText(HttpExchange h) throws IOException {
		return new String(h.getRequestBody().readAllBytes(), UTF_8);
	}

	protected void sendText(HttpExchange h, String text) throws IOException {
		byte[] resp = text.getBytes(UTF_8);
		h.getResponseHeaders().add("Content-Type", "application/json");
		h.sendResponseHeaders(HTTP_OK, resp.length);
		h.getResponseBody().write(resp);
	}
}
