package com.github.ngyewch.gradle;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class GitDescribePlugin
    implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    if (project.getVersion().equals("unspecified") && (System.getenv("GIT_DESCRIBE_VERSION") != null)) {
      project.setVersion(System.getenv("GIT_DESCRIBE_VERSION"));
    }
    if (project.getVersion().equals("unspecified")) {
      try {
        final FileRepositoryBuilder builder = new FileRepositoryBuilder();
        final Repository repository = builder.setGitDir(project.getRootDir())
            .readEnvironment()
            .findGitDir()
            .build();
        final Git git = new Git(repository);
        project.setVersion(git.describe().setTags(true).call());
      } catch (Exception e) {
        project.getLogger().warn("Could not execute git describe: " + e);
        final String defaultVersion = System.getenv("DEFAULT_GIT_DESCRIBE_VERSION");
        if (defaultVersion != null) {
          project.setVersion(defaultVersion);
        }
      }
    }
  }
}
