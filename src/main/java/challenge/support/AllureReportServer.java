package challenge.support;

import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.Map;

/**
 * Serves a generated Allure report locally. Allure's report loads its data
 * via XHR, which browsers block under file:// (verified: opening the report
 * directly shows a blank page and CORS errors in the console) - this exists
 * so viewing it doesn't require `allure serve` or a separate CLI install.
 * Run with: mvn test allure:report exec:java@view-allure-report
 */
public final class AllureReportServer {

    private static final Map<String, String> MIME_TYPES = Map.ofEntries(
            Map.entry("html", "text/html"),
            Map.entry("js", "application/javascript"),
            Map.entry("css", "text/css"),
            Map.entry("json", "application/json"),
            Map.entry("ico", "image/x-icon"),
            Map.entry("png", "image/png"),
            Map.entry("svg", "image/svg+xml"),
            Map.entry("woff", "font/woff"),
            Map.entry("woff2", "font/woff2"),
            Map.entry("ttf", "font/ttf"),
            Map.entry("txt", "text/plain"),
            Map.entry("csv", "text/csv"),
            Map.entry("xml", "application/xml"));

    private AllureReportServer() {
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        File reportDir = new File("target/site/allure-maven-plugin").getCanonicalFile();
        if (!reportDir.isDirectory()) {
            System.err.println("No report found at " + reportDir + " - run `mvn test allure:report` first.");
            return;
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(0), 0);
        server.createContext("/", exchange -> {
            String requestPath = exchange.getRequestURI().getPath();
            String relative = requestPath.equals("/") ? "/index.html" : requestPath;
            File target = new File(reportDir, relative).getCanonicalFile();
            if (!target.isFile() || !target.getPath().startsWith(reportDir.getPath())) {
                exchange.sendResponseHeaders(404, -1);
                return;
            }
            String name = target.getName();
            String ext = name.contains(".") ? name.substring(name.lastIndexOf('.') + 1) : "";
            byte[] bytes = Files.readAllBytes(target.toPath());
            exchange.getResponseHeaders().add("Content-Type", MIME_TYPES.getOrDefault(ext, "application/octet-stream"));
            exchange.sendResponseHeaders(200, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.getResponseBody().close();
        });
        server.start();

        System.out.println();
        System.out.println("Allure report: http://localhost:" + server.getAddress().getPort() + "/");
        System.out.println("Press Ctrl+C to stop.");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop(0)));
        Thread.currentThread().join();
    }
}
