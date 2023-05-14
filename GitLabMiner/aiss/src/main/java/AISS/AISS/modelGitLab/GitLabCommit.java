package AISS.AISS.modelGitLab;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitLabCommit {

    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String title;

    @JsonProperty("message")
    private String message;
    @JsonProperty("author_name")
    private String authorName;
    @JsonProperty("author_email")
    private String authorEmail;
    @JsonProperty("authored_date")
    private String authoredDate;
    @JsonProperty("committer_name")
    private String committerName;
    @JsonProperty("committer_email")
    private String committerEmail;

    @JsonProperty("committed_date")
    private String committedDate;

    @JsonProperty("web_url")
    private String webUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getAuthoredDate() {
        return authoredDate;
    }

    public void setAuthoredDate(String authoredDate) {
        this.authoredDate = authoredDate;
    }

    public String getCommitterName() {
        return committerName;
    }

    public void setCommitterName(String committerName) {
        this.committerName = committerName;
    }

    public String getCommitterEmail() {
        return committerEmail;
    }

    public void setCommitterEmail(String committerEmail) {
        this.committerEmail = committerEmail;
    }

    public String getCommittedDate() {
        return committedDate;
    }

    public void setCommittedDate(String committedDate) {
        this.committedDate = committedDate;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public GitLabCommit() {
    }

    public GitLabCommit(String id, String title, String message, String authorName, String authorEmail, String authoredDate,
                        String committerName, String committerEmail, String committedDate, String webUrl) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.authoredDate =authoredDate;
        this.committerName = committerName;
        this.committerEmail = committerEmail;
        this.committedDate = committedDate;
        this.webUrl = webUrl;
    }

    @Override
    public String toString() {
        return "Commit{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorEmail='" + authorEmail + '\'' +
                ", authoredDate='" + authoredDate + '\'' +
                ", committerName='" + committerName + '\'' +
                ", committerEmail='" + committerEmail + '\'' +
                ", committedDate='" + committedDate + '\'' +
                ", webUrl='" + webUrl + '\'' +
                '}';
    }
}
