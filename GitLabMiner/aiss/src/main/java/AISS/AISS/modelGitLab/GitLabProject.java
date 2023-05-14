
package AISS.AISS.modelGitLab;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class GitLabProject {

    @JsonProperty("id")
    public String id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("web_url")
    public String webUrl;
    @JsonProperty("commits")
    private List<GitLabCommit> commits;

    @JsonProperty("issues")
    private List<GitLabIssue> issues;

    public GitLabProject(String id, String name, String webUrl, List<GitLabCommit> commits, List<GitLabIssue> issues) {
        this.id = id;
        this.name = name;
        this.webUrl = webUrl;
        this.commits = commits;
        this.issues = issues;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public List<GitLabCommit> getCommits() {
        return commits;
    }

    public void setCommits(List<GitLabCommit> commits) {
        this.commits = commits;
    }

    public List<GitLabIssue> getIssues() {
        return issues;
    }

    public void setIssues(List<GitLabIssue> issues) {
        this.issues = issues;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", webUrl='" + webUrl + '\'' +
                ", commits=" + commits +
                ", issues=" + issues +
                '}';
    }
}
