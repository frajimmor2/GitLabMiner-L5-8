package AISS.AISS.service;

import AISS.AISS.model.Comment;
import AISS.AISS.model.Commit;
import AISS.AISS.model.Issue;
import AISS.AISS.model.Project;
import AISS.AISS.modelGitLab.GitLabComment;
import AISS.AISS.modelGitLab.GitLabCommit;
import AISS.AISS.modelGitLab.GitLabIssue;
import AISS.AISS.modelGitLab.GitLabProject;
import AISS.AISS.util.GitLabUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class GitLabService {

    @Autowired
    RestTemplate restTemplate;

    private String token;

    public Project getProjectId(String id) {
        String uri = "https://gitlab.com/api/v4/projects/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", token);
        HttpEntity<Project> req = new HttpEntity<>(null, headers);
        ResponseEntity<Project> response = restTemplate.exchange(uri, HttpMethod.GET, req, Project.class);
        return response.getBody();
    }

    public ResponseEntity<Commit[]> getAllCommits(String id) {
        String uri = "https://gitlab.com/api/v4/projects/" + id + "/repository/commits";
        HttpHeaders headers = new HttpHeaders();
        headers.set("PRIVATE-TOKEN", token);
        HttpEntity<Commit[]> request = new HttpEntity<>(null, headers);
        return restTemplate.exchange(uri, HttpMethod.GET, request, Commit[].class);
    }

    public ResponseEntity<Issue[]> getAllIssues(String id) {
        String uri = "https://gitlab.com/api/v4/projects/" + id + "/issues";
        HttpHeaders headers = new HttpHeaders();
        headers.set("PRIVATE-TOKEN", token);
        HttpEntity<Issue[]> request = new HttpEntity<>(null, headers);
        return restTemplate.exchange(uri, HttpMethod.GET, request, Issue[].class);
    }

    public ResponseEntity<Comment[]> getIssueComments(String id, String issue_id) {
        String uri = "https://gitlab.com/api/v4/projects/" + id + "/issues/" + issue_id + "/notes";
        HttpHeaders headers = new HttpHeaders();
        headers.set("PRIVATE-TOKEN", token);
        HttpEntity<Comment[]> request = new HttpEntity<>(null, headers);
        return restTemplate.exchange(uri, HttpMethod.GET, request, Comment[].class);
    }

    public List<Commit> groupAllCommits(String id, Integer since, Integer maxPages) {
        List<Commit> commits = new ArrayList<>();
        String uri = "https://gitlab.com/api/v4/projects/" + id + "/repository/commits/";
        Integer pages;
        if (since != null) {
            uri += "?since=" + LocalDateTime.now().minusDays(since);
        } else {
            uri += "?since=" + LocalDateTime.now().minusDays(2);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("PRIVATE-TOKEN", token);
        HttpEntity<Commit[]> request = new HttpEntity<>(null, headers);
        ResponseEntity<Commit[]> response = restTemplate.exchange(uri, HttpMethod.GET, request, Commit[].class);
        List<Commit> pageCommits = Arrays.stream(response.getBody()).toList();
        commits.addAll(pageCommits);

        String nextPageURL = GitLabUtils.getNextPageUrl(response.getHeaders());

        if(maxPages!=null){
            pages=maxPages;
        }
        else{
            pages=2;
        }

        int page = 2;
        while (nextPageURL != null && page <= pages) {
            response = restTemplate.exchange(nextPageURL, HttpMethod.GET, request, Commit[].class);
            pageCommits = Arrays.stream(response.getBody()).toList();
            commits.addAll(pageCommits);

            nextPageURL = GitLabUtils.getNextPageUrl(response.getHeaders());
            page++;

        }
        return commits;

    }

    public List<Issue> groupAllIssues(String id, Integer updated_after, Integer maxPages) throws HttpClientErrorException {
        List<Issue> issues = new ArrayList<>();
        Integer defaultPages;
        String uri = "https://gitlab.com/api/v4/projects/" + id + "/issues";

        if (updated_after != null) {
            uri += "/?updated_after=" + LocalDateTime.now().minusDays(updated_after);
        } else {
            uri += "/?updated_after=" + LocalDateTime.now().minusDays(20);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("PRIVATE-TOKEN", token);
        HttpEntity<Issue[]> request = new HttpEntity<>(null, headers);
        ResponseEntity<Issue[]> response = restTemplate.exchange(uri, HttpMethod.GET, request, Issue[].class);
        List<Issue> pageIssues = Arrays.stream(response.getBody()).toList();
        issues.addAll(pageIssues);

        String nextPageURL = GitLabUtils.getNextPageUrl(response.getHeaders());

        if(maxPages!=null){
            defaultPages=maxPages;
        }
        else{
            defaultPages=2;
        }

        int page = 2;
        while (nextPageURL != null && page <= defaultPages) {
            response = response = restTemplate.exchange(nextPageURL, HttpMethod.GET, request, Issue[].class);
            pageIssues = Arrays.stream(response.getBody()).toList();
            issues.addAll(pageIssues);
            nextPageURL = GitLabUtils.getNextPageUrl(response.getHeaders());
            page++;
        }
        return issues;
    }

    public List<Comment> groupIssueComments(String id, String issue_iid, Integer maxPages) throws HttpClientErrorException{
        List<Comment> comments = new ArrayList<>();
        Integer defaultPages;
        String uri = "https://gitlab.com/api/v4/projects/" + id + "/issues/" + issue_iid + "/notes";

        HttpHeaders headers = new HttpHeaders();
        headers.set("PRIVATE-TOKEN", token);
        HttpEntity<Comment[]> request = new HttpEntity<>(null, headers);
        ResponseEntity<Comment[]> response = restTemplate.exchange(uri, HttpMethod.GET, request, Comment[].class);
        List<Comment> pageComments = Arrays.stream(response.getBody()).toList();
        comments.addAll(pageComments);

        //2..n pages
        String nextPageURL = GitLabUtils.getNextPageUrl(response.getHeaders());

        if(maxPages!=null){
            defaultPages=maxPages;
        }
        else{
            defaultPages=2;
        }

        int page = 2;
        while(nextPageURL != null && page <= defaultPages){
            response = restTemplate.exchange(nextPageURL, HttpMethod.GET, request, Comment[].class);
            pageComments = Arrays.stream(response.getBody()).toList();
            comments.addAll(pageComments);

            nextPageURL = GitLabUtils.getNextPageUrl(response.getHeaders());
            page++;
        }

        return comments;
    }
}