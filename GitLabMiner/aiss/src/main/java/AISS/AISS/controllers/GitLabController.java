package AISS.AISS.controllers;

import AISS.AISS.model.Comment;
import AISS.AISS.model.Commit;
import AISS.AISS.model.Issue;
import AISS.AISS.model.Project;
import AISS.AISS.modelGitLab.*;
import AISS.AISS.service.GitLabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/gitlab")
public class GitLabController {
    @Autowired
    GitLabService service;

    @Autowired
    RestTemplate restTemplate;

// GET http://localhost:8081/github/{id}[?sinceCommits=2&sinceIssues=20&maxPages=2]
    @GetMapping("/{id}")
    public GitLabProject findProject(@PathVariable String id, @RequestParam(required = false) Integer sinceCommits,
                                     @RequestParam(required = false) Integer sinceIssues, @RequestParam(required = false) Integer maxPages) {
        Project project = service.getProjectId(id);
        String projectId = project.getId().toString();
        String projectName = project.getName();
        String project_webUrl = project.getWebUrl();
        List<GitLabCommit> commits = service.groupAllCommits(id,sinceCommits,maxPages).stream().map(x->formatCommit(x)).toList();
        List<GitLabIssue> issues = service.groupAllIssues(id, sinceIssues, maxPages).stream().map(x->formatIssue(x,id,maxPages)).toList();

        GitLabProject newProject = new GitLabProject(projectId, projectName, project_webUrl, commits, issues);

        return newProject;
    }

    private GitLabCommit formatCommit(Commit commit) {
        return new GitLabCommit(commit.getId(),commit.getTitle(),commit.getMessage(),
                commit.getAuthorName(),commit.getAuthorEmail(),commit.getAuthoredDate(),
                commit.getCommitterName(),commit.getCommitterEmail(),commit.getCommittedDate(),commit.getWebUrl());
    }

    private GitLabIssue formatIssue(Issue issue, String id, Integer maxPages) {
        String issueId = issue.getId().toString();
        String ref_id = issue.getIid().toString();
        String title = issue.getTitle();
        String description = issue.getDescription();
        String state = issue.getState();
        String created_at = issue.getCreatedAt();
        String updated_at = issue.getUpdatedAt();
        String closed_at = issue.getClosedAt();
        List<String> labels = issue.getLabels();
        GitLabUser author = new GitLabUser(issue.getAuthor().getId().toString(), issue.getAuthor().getUsername(),issue.getAuthor().getName(),issue.getAuthor().getAvatarUrl(),issue.getAuthor().getWebUrl());

        GitLabUser assignee = issue.getAssignee()==null?null:new GitLabUser(issue.getAssignee().getId().toString(), issue.getAssignee().getUsername(), issue.getAssignee().getName(), issue.getAssignee().getAvatarUrl(), issue.getAssignee().getWebUrl());
        //GitLabMinerUser assignee = null;
        Integer upvotes = issue.getUpvotes();
        Integer downvotes = issue.getDownvotes();
        String web_url = issue.getWebUrl();


        return new GitLabIssue(issueId, ref_id, title, description, state, created_at, updated_at, closed_at, labels,
                author,assignee,upvotes,downvotes,web_url);
    }

    private GitLabComment formatComment(Comment comment){
        String id = comment.getId().toString();
        String body = comment.getBody();
        GitLabUser author = new GitLabUser(comment.getAuthor().getId().toString(), comment.getAuthor().getUsername(),comment.getAuthor().getName(),comment.getAuthor().getAvatarUrl(),comment.getAuthor().getWebUrl());
        String created_at = comment.getCreatedAt();
        String updated_at = comment.getUpdatedAt();
        return new GitLabComment(id, body, author, created_at, updated_at);
    }

    @PostMapping("/{id}")
    public GitLabProject createProject(@PathVariable String id, @RequestParam(required = false) Integer sinceCommits,
                                       @RequestParam(required = false) Integer sinceIssues, @RequestParam(required = false) Integer maxPages) {
        Project project = service.getProjectId(id);
        String projectId = project.getId().toString();
        String projectName = project.getName();
        String project_webUrl = project.getWebUrl();
        List<GitLabCommit> commits = service.groupAllCommits(id,sinceCommits,maxPages).stream().map(x->formatCommit(x)).toList();
        List<GitLabIssue> issues = service.groupAllIssues(id, sinceIssues, maxPages).stream().map(x->formatIssue(x,id,maxPages)).toList();

        GitLabProject proyectoFormateado = new GitLabProject(projectId,projectName,project_webUrl,commits,issues);


        GitLabProject sentProject = restTemplate.postForObject("http://localhost:8080/gitminer/projects", proyectoFormateado,GitLabProject.class);

        return sentProject;
    }


}
