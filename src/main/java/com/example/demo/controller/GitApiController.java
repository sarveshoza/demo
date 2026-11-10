package com.example.demo.controller;

import com.example.demo.model.Repos;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;

@RestController
public class GitApiController {

    @Value("${user.url}")
    String userUrl;

    @GetMapping("/repos")
    public String getRepos() {
        RestClient restClient = RestClient.builder().baseUrl(userUrl).build();
        List<Repos> reposList = restClient.get()
                .uri("/repos")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Repos>>() {});
        StringBuilder html = new StringBuilder();
        html.append("<table border='1' style='width: 100%; text-align: center;'><tr><th colspan='4'>Repositories</th></tr>" +
                "<tr><th>Name</th><th>Full Name</th><th>Private</th><th>URL</th></tr>");
        if (reposList != null) {
            for (Repos repo : reposList) {
                html.append("<tr>");
                html.append("<td>").append(repo.getName()).append("</td>");
                html.append("<td>").append(repo.getFullName()).append("</td>");
                html.append("<td>").append(repo.isPrivate()).append("</td>");
                html.append("<td><a href='")
                    .append(repo.getHtmlUrl())
                    .append("' target='_blank'>")
                    .append(repo.getHtmlUrl())
                    .append("</a></td>");
                html.append("</tr>");
            }
        }
        html.append("</table>");
        return html.toString();
    }

    @GetMapping(value = "/repos/{repo}/pulls", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getPullRequests(@PathVariable String repo) {
        RestClient restClient = RestClient.builder().baseUrl("https://api.github.com").build();
        List<Object> pulls = restClient.get()
                .uri("/repos/sarveshoza/" + repo + "/pulls")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Object>>() {});
        StringBuilder html = null;
        if (pulls != null) {
            html = getStringBuilder(pulls);
        }
        return Objects.requireNonNull(html).toString();
    }

    private static StringBuilder getStringBuilder(List<Object> pulls) {
        StringBuilder html = new StringBuilder();
        html.append("<table border='1'><tr><th>Number</th><th>Title</th><th>User</th><th>State</th><th>Url</th></tr>");
        for (Object pull : pulls) {
            var map = (java.util.Map<?, ?>) pull;
            html.append("<tr>");
            html.append("<td>").append(map.get("number")).append("</td>");
            html.append("<td>").append(map.get("title")).append("</td>");
            var user = (java.util.Map<?, ?>) map.get("user");
            html.append("<td>").append(user != null ? user.get("login") : "").append("</td>");
            html.append("<td>").append(map.get("state")).append("</td>");
            html.append("<td><a href='")
                .append(map.get("html_url"))
                .append("' target='_blank'>")
                .append(map.get("html_url"))
                .append("</a></td>");
            html.append("</tr>");
        }
        html.append("</table>");
        return html;
    }

    @GetMapping("/repos/{repo}/pulls/all")
    public static String getAllPullRequests(@PathVariable String repo) {
        RestClient restClient = RestClient.builder().baseUrl("https://api.github.com").build();
        List<Object> pulls = restClient.get()
                .uri("/repos/sarveshoza/" + repo + "/pulls?state=all")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Object>>() {});
        if (pulls != null) {
            return getStringBuilder(pulls).toString();
        }
        return "<h1>No Pull Requests Found</h1>";}


}
