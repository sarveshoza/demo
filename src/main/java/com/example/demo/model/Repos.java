package com.example.demo.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Repos {
    private long id;
    @JsonProperty("node_id")
    private String nodeId;
    private String name;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("private")
    private boolean isPrivate;
    private Owner owner;
    @JsonProperty("html_url")
    private String htmlUrl;
    private String description;
    private boolean fork;
    private String url;
    @JsonProperty("forks_url")
    private String forksUrl;
    @JsonProperty("keys_url")
    private String keysUrl;
    @JsonProperty("collaborators_url")
    private String collaboratorsUrl;
    @JsonProperty("teams_url")
    private String teamsUrl;
    @JsonProperty("hooks_url")
    private String hooksUrl;
    @JsonProperty("issue_events_url")
    private String issueEventsUrl;
    @JsonProperty("events_url")
    private String eventsUrl;
    @JsonProperty("assignees_url")
    private String assigneesUrl;
    @JsonProperty("branches_url")
    private String branchesUrl;
    @JsonProperty("tags_url")
    private String tagsUrl;
    @JsonProperty("blobs_url")
    private String blobsUrl;
    @JsonProperty("git_tags_url")
    private String gitTagsUrl;
    @JsonProperty("git_refs_url")
    private String gitRefsUrl;
    @JsonProperty("trees_url")
    private String treesUrl;
    @JsonProperty("statuses_url")
    private String statusesUrl;
    @JsonProperty("languages_url")
    private String languagesUrl;
    @JsonProperty("stargazers_url")
    private String stargazersUrl;
    @JsonProperty("contributors_url")
    private String contributorsUrl;
    @JsonProperty("subscribers_url")
    private String subscribersUrl;
    @JsonProperty("subscription_url")
    private String subscriptionUrl;
    @JsonProperty("commits_url")
    private String commitsUrl;
    @JsonProperty("git_commits_url")
    private String gitCommitsUrl;
    @JsonProperty("comments_url")
    private String commentsUrl;
    @JsonProperty("issue_comment_url")
    private String issueCommentUrl;
    @JsonProperty("contents_url")
    private String contentsUrl;
    @JsonProperty("compare_url")
    private String compareUrl;
    @JsonProperty("merges_url")
    private String mergesUrl;
    @JsonProperty("archive_url")
    private String archiveUrl;
    @JsonProperty("downloads_url")
    private String downloadsUrl;
    @JsonProperty("issues_url")
    private String issuesUrl;
    @JsonProperty("pulls_url")
    private String pullsUrl;
    @JsonProperty("milestones_url")
    private String milestonesUrl;
    @JsonProperty("notifications_url")
    private String notificationsUrl;
    @JsonProperty("labels_url")
    private String labelsUrl;
    @JsonProperty("releases_url")
    private String releasesUrl;
    @JsonProperty("deployments_url")
    private String deploymentsUrl;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("pushed_at")
    private String pushedAt;
    @JsonProperty("git_url")
    private String gitUrl;
    @JsonProperty("ssh_url")
    private String sshUrl;
    @JsonProperty("clone_url")
    private String cloneUrl;
    @JsonProperty("svn_url")
    private String svnUrl;
    private String homepage;
    private int size;
    @JsonProperty("stargazers_count")
    private int stargazersCount;
    @JsonProperty("watchers_count")
    private int watchersCount;
    private String language;
    @JsonProperty("has_issues")
    private boolean hasIssues;
    @JsonProperty("has_projects")
    private boolean hasProjects;
    @JsonProperty("has_downloads")
    private boolean hasDownloads;
    @JsonProperty("has_wiki")
    private boolean hasWiki;
    @JsonProperty("has_pages")
    private boolean hasPages;
    @JsonProperty("has_discussions")
    private boolean hasDiscussions;
    @JsonProperty("forks_count")
    private int forksCount;
    @JsonProperty("mirror_url")
    private String mirrorUrl;
    private boolean archived;
    private boolean disabled;
    @JsonProperty("open_issues_count")
    private int openIssuesCount;
    private String license;
    @JsonProperty("allow_forking")
    private boolean allowForking;
    @JsonProperty("is_template")
    private boolean isTemplate;
    @JsonProperty("web_commit_signoff_required")
    private boolean webCommitSignoffRequired;
    private List<String> topics;
    private String visibility;
    private int forks;
    @JsonProperty("open_issues")
    private int openIssues;
    private int watchers;
    @JsonProperty("default_branch")
    private String defaultBranch;

    // Getters and setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getNodeId() { return nodeId; }
    public void setNodeId(String nodeId) { this.nodeId = nodeId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public boolean isPrivate() { return isPrivate; }
    public void setPrivate(boolean isPrivate) { this.isPrivate = isPrivate; }
    public Owner getOwner() { return owner; }
    public void setOwner(Owner owner) { this.owner = owner; }
    public String getHtmlUrl() { return htmlUrl; }
    public void setHtmlUrl(String htmlUrl) { this.htmlUrl = htmlUrl; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isFork() { return fork; }
    public void setFork(boolean fork) { this.fork = fork; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getForksUrl() { return forksUrl; }
    public void setForksUrl(String forksUrl) { this.forksUrl = forksUrl; }
    public String getKeysUrl() { return keysUrl; }
    public void setKeysUrl(String keysUrl) { this.keysUrl = keysUrl; }
    public String getCollaboratorsUrl() { return collaboratorsUrl; }
    public void setCollaboratorsUrl(String collaboratorsUrl) { this.collaboratorsUrl = collaboratorsUrl; }
    public String getTeamsUrl() { return teamsUrl; }
    public void setTeamsUrl(String teamsUrl) { this.teamsUrl = teamsUrl; }
    public String getHooksUrl() { return hooksUrl; }
    public void setHooksUrl(String hooksUrl) { this.hooksUrl = hooksUrl; }
    public String getIssueEventsUrl() { return issueEventsUrl; }
    public void setIssueEventsUrl(String issueEventsUrl) { this.issueEventsUrl = issueEventsUrl; }
    public String getEventsUrl() { return eventsUrl; }
    public void setEventsUrl(String eventsUrl) { this.eventsUrl = eventsUrl; }
    public String getAssigneesUrl() { return assigneesUrl; }
    public void setAssigneesUrl(String assigneesUrl) { this.assigneesUrl = assigneesUrl; }
    public String getBranchesUrl() { return branchesUrl; }
    public void setBranchesUrl(String branchesUrl) { this.branchesUrl = branchesUrl; }
    public String getTagsUrl() { return tagsUrl; }
    public void setTagsUrl(String tagsUrl) { this.tagsUrl = tagsUrl; }
    public String getBlobsUrl() { return blobsUrl; }
    public void setBlobsUrl(String blobsUrl) { this.blobsUrl = blobsUrl; }
    public String getGitTagsUrl() { return gitTagsUrl; }
    public void setGitTagsUrl(String gitTagsUrl) { this.gitTagsUrl = gitTagsUrl; }
    public String getGitRefsUrl() { return gitRefsUrl; }
    public void setGitRefsUrl(String gitRefsUrl) { this.gitRefsUrl = gitRefsUrl; }
    public String getTreesUrl() { return treesUrl; }
    public void setTreesUrl(String treesUrl) { this.treesUrl = treesUrl; }
    public String getStatusesUrl() { return statusesUrl; }
    public void setStatusesUrl(String statusesUrl) { this.statusesUrl = statusesUrl; }
    public String getLanguagesUrl() { return languagesUrl; }
    public void setLanguagesUrl(String languagesUrl) { this.languagesUrl = languagesUrl; }
    public String getStargazersUrl() { return stargazersUrl; }
    public void setStargazersUrl(String stargazersUrl) { this.stargazersUrl = stargazersUrl; }
    public String getContributorsUrl() { return contributorsUrl; }
    public void setContributorsUrl(String contributorsUrl) { this.contributorsUrl = contributorsUrl; }
    public String getSubscribersUrl() { return subscribersUrl; }
    public void setSubscribersUrl(String subscribersUrl) { this.subscribersUrl = subscribersUrl; }
    public String getSubscriptionUrl() { return subscriptionUrl; }
    public void setSubscriptionUrl(String subscriptionUrl) { this.subscriptionUrl = subscriptionUrl; }
    public String getCommitsUrl() { return commitsUrl; }
    public void setCommitsUrl(String commitsUrl) { this.commitsUrl = commitsUrl; }
    public String getGitCommitsUrl() { return gitCommitsUrl; }
    public void setGitCommitsUrl(String gitCommitsUrl) { this.gitCommitsUrl = gitCommitsUrl; }
    public String getCommentsUrl() { return commentsUrl; }
    public void setCommentsUrl(String commentsUrl) { this.commentsUrl = commentsUrl; }
    public String getIssueCommentUrl() { return issueCommentUrl; }
    public void setIssueCommentUrl(String issueCommentUrl) { this.issueCommentUrl = issueCommentUrl; }
    public String getContentsUrl() { return contentsUrl; }
    public void setContentsUrl(String contentsUrl) { this.contentsUrl = contentsUrl; }
    public String getCompareUrl() { return compareUrl; }
    public void setCompareUrl(String compareUrl) { this.compareUrl = compareUrl; }
    public String getMergesUrl() { return mergesUrl; }
    public void setMergesUrl(String mergesUrl) { this.mergesUrl = mergesUrl; }
    public String getArchiveUrl() { return archiveUrl; }
    public void setArchiveUrl(String archiveUrl) { this.archiveUrl = archiveUrl; }
    public String getDownloadsUrl() { return downloadsUrl; }
    public void setDownloadsUrl(String downloadsUrl) { this.downloadsUrl = downloadsUrl; }
    public String getIssuesUrl() { return issuesUrl; }
    public void setIssuesUrl(String issuesUrl) { this.issuesUrl = issuesUrl; }
    public String getPullsUrl() { return pullsUrl; }
    public void setPullsUrl(String pullsUrl) { this.pullsUrl = pullsUrl; }
    public String getMilestonesUrl() { return milestonesUrl; }
    public void setMilestonesUrl(String milestonesUrl) { this.milestonesUrl = milestonesUrl; }
    public String getNotificationsUrl() { return notificationsUrl; }
    public void setNotificationsUrl(String notificationsUrl) { this.notificationsUrl = notificationsUrl; }
    public String getLabelsUrl() { return labelsUrl; }
    public void setLabelsUrl(String labelsUrl) { this.labelsUrl = labelsUrl; }
    public String getReleasesUrl() { return releasesUrl; }
    public void setReleasesUrl(String releasesUrl) { this.releasesUrl = releasesUrl; }
    public String getDeploymentsUrl() { return deploymentsUrl; }
    public void setDeploymentsUrl(String deploymentsUrl) { this.deploymentsUrl = deploymentsUrl; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
    public String getPushedAt() { return pushedAt; }
    public void setPushedAt(String pushedAt) { this.pushedAt = pushedAt; }
    public String getGitUrl() { return gitUrl; }
    public void setGitUrl(String gitUrl) { this.gitUrl = gitUrl; }
    public String getSshUrl() { return sshUrl; }
    public void setSshUrl(String sshUrl) { this.sshUrl = sshUrl; }
    public String getCloneUrl() { return cloneUrl; }
    public void setCloneUrl(String cloneUrl) { this.cloneUrl = cloneUrl; }
    public String getSvnUrl() { return svnUrl; }
    public void setSvnUrl(String svnUrl) { this.svnUrl = svnUrl; }
    public String getHomepage() { return homepage; }
    public void setHomepage(String homepage) { this.homepage = homepage; }
    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }
    public int getStargazersCount() { return stargazersCount; }
    public void setStargazersCount(int stargazersCount) { this.stargazersCount = stargazersCount; }
    public int getWatchersCount() { return watchersCount; }
    public void setWatchersCount(int watchersCount) { this.watchersCount = watchersCount; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public boolean isHasIssues() { return hasIssues; }
    public void setHasIssues(boolean hasIssues) { this.hasIssues = hasIssues; }
    public boolean isHasProjects() { return hasProjects; }
    public void setHasProjects(boolean hasProjects) { this.hasProjects = hasProjects; }
    public boolean isHasDownloads() { return hasDownloads; }
    public void setHasDownloads(boolean hasDownloads) { this.hasDownloads = hasDownloads; }
    public boolean isHasWiki() { return hasWiki; }
    public void setHasWiki(boolean hasWiki) { this.hasWiki = hasWiki; }
    public boolean isHasPages() { return hasPages; }
    public void setHasPages(boolean hasPages) { this.hasPages = hasPages; }
    public boolean isHasDiscussions() { return hasDiscussions; }
    public void setHasDiscussions(boolean hasDiscussions) { this.hasDiscussions = hasDiscussions; }
    public int getForksCount() { return forksCount; }
    public void setForksCount(int forksCount) { this.forksCount = forksCount; }
    public String getMirrorUrl() { return mirrorUrl; }
    public void setMirrorUrl(String mirrorUrl) { this.mirrorUrl = mirrorUrl; }
    public boolean isArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }
    public boolean isDisabled() { return disabled; }
    public void setDisabled(boolean disabled) { this.disabled = disabled; }
    public int getOpenIssuesCount() { return openIssuesCount; }
    public void setOpenIssuesCount(int openIssuesCount) { this.openIssuesCount = openIssuesCount; }
    public String getLicense() { return license; }
    public void setLicense(String license) { this.license = license; }
    public boolean isAllowForking() { return allowForking; }
    public void setAllowForking(boolean allowForking) { this.allowForking = allowForking; }
    public boolean isTemplate() { return isTemplate; }
    public void setTemplate(boolean isTemplate) { this.isTemplate = isTemplate; }
    public boolean isWebCommitSignoffRequired() { return webCommitSignoffRequired; }
    public void setWebCommitSignoffRequired(boolean webCommitSignoffRequired) { this.webCommitSignoffRequired = webCommitSignoffRequired; }
    public List<String> getTopics() { return topics; }
    public void setTopics(List<String> topics) { this.topics = topics; }
    public String getVisibility() { return visibility; }
    public void setVisibility(String visibility) { this.visibility = visibility; }
    public int getForks() { return forks; }
    public void setForks(int forks) { this.forks = forks; }
    public int getOpenIssues() { return openIssues; }
    public void setOpenIssues(int openIssues) { this.openIssues = openIssues; }
    public int getWatchers() { return watchers; }
    public void setWatchers(int watchers) { this.watchers = watchers; }
    public String getDefaultBranch() { return defaultBranch; }
    public void setDefaultBranch(String defaultBranch) { this.defaultBranch = defaultBranch; }

    public static class Owner {
        private String login;
        private long id;
        @JsonProperty("node_id")
        private String nodeId;
        @JsonProperty("avatar_url")
        private String avatarUrl;
        @JsonProperty("gravatar_id")
        private String gravatarId;
        private String url;
        @JsonProperty("html_url")
        private String htmlUrl;
        @JsonProperty("followers_url")
        private String followersUrl;
        @JsonProperty("following_url")
        private String followingUrl;
        @JsonProperty("gists_url")
        private String gistsUrl;
        @JsonProperty("starred_url")
        private String starredUrl;
        @JsonProperty("subscriptions_url")
        private String subscriptionsUrl;
        @JsonProperty("organizations_url")
        private String organizationsUrl;
        @JsonProperty("repos_url")
        private String reposUrl;
        @JsonProperty("events_url")
        private String eventsUrl;
        @JsonProperty("received_events_url")
        private String receivedEventsUrl;
        private String type;
        @JsonProperty("user_view_type")
        private String userViewType;
        @JsonProperty("site_admin")
        private boolean siteAdmin;

        // Getters and setters
        public String getLogin() { return login; }
        public void setLogin(String login) { this.login = login; }
        public long getId() { return id; }
        public void setId(long id) { this.id = id; }
        public String getNodeId() { return nodeId; }
        public void setNodeId(String nodeId) { this.nodeId = nodeId; }
        public String getAvatarUrl() { return avatarUrl; }
        public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
        public String getGravatarId() { return gravatarId; }
        public void setGravatarId(String gravatarId) { this.gravatarId = gravatarId; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        public String getHtmlUrl() { return htmlUrl; }
        public void setHtmlUrl(String htmlUrl) { this.htmlUrl = htmlUrl; }
        public String getFollowersUrl() { return followersUrl; }
        public void setFollowersUrl(String followersUrl) { this.followersUrl = followersUrl; }
        public String getFollowingUrl() { return followingUrl; }
        public void setFollowingUrl(String followingUrl) { this.followingUrl = followingUrl; }
        public String getGistsUrl() { return gistsUrl; }
        public void setGistsUrl(String gistsUrl) { this.gistsUrl = gistsUrl; }
        public String getStarredUrl() { return starredUrl; }
        public void setStarredUrl(String starredUrl) { this.starredUrl = starredUrl; }
        public String getSubscriptionsUrl() { return subscriptionsUrl; }
        public void setSubscriptionsUrl(String subscriptionsUrl) { this.subscriptionsUrl = subscriptionsUrl; }
        public String getOrganizationsUrl() { return organizationsUrl; }
        public void setOrganizationsUrl(String organizationsUrl) { this.organizationsUrl = organizationsUrl; }
        public String getReposUrl() { return reposUrl; }
        public void setReposUrl(String reposUrl) { this.reposUrl = reposUrl; }
        public String getEventsUrl() { return eventsUrl; }
        public void setEventsUrl(String eventsUrl) { this.eventsUrl = eventsUrl; }
        public String getReceivedEventsUrl() { return receivedEventsUrl; }
        public void setReceivedEventsUrl(String receivedEventsUrl) { this.receivedEventsUrl = receivedEventsUrl; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getUserViewType() { return userViewType; }
        public void setUserViewType(String userViewType) { this.userViewType = userViewType; }
        public boolean isSiteAdmin() { return siteAdmin; }
        public void setSiteAdmin(boolean siteAdmin) { this.siteAdmin = siteAdmin; }
    }
}

