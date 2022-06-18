package com.github.ngyewch.gradle;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class GitDescribePlugin
    implements Plugin<Project> {

  @Override
  public void apply(Project project) {
    if (!isVersionSpecified(project)) {
      final Object projectVersion = resolveVersion(project);
      if (projectVersion != null) {
        project.setVersion(projectVersion);
      }
    }
  }

  private boolean isVersionSpecified(Project project) {
    return !project.getVersion().equals("unspecified");
  }

  private Object resolveVersion(Project project) {
    {
      final String gitDescribeVersion = System.getenv("GIT_DESCRIBE_VERSION");
      if (gitDescribeVersion != null) {
        if (gitDescribeVersion.startsWith("v")) {
          return gitDescribeVersion.substring(1);
        } else {
          throw new GradleException("GIT_DESCRIBE_VERSION must start with 'v'");
        }
      }
    }

    try {
      final Repository repository = new FileRepositoryBuilder()
          .readEnvironment()
          .findGitDir(project.getRootDir())
          .build();
      final Git git = new Git(repository);
      final String gitDescribeOutput = git.describe()
          .setTags(true)
          .setMatch("v*")
          .call();
      if (gitDescribeOutput != null) {
        final boolean clean = git.status().call().isClean();
        final String gitDescribeVersion = String.format("%s%s", gitDescribeOutput, clean ? "" : "-dirty");
        return gitDescribeVersion.substring(1);
      }
    } catch (Exception e) {
      project.getLogger().warn("Could not execute git describe: " + e);
    }

    {
      final String defaultGitDescribeVersion = System.getenv("DEFAULT_GIT_DESCRIBE_VERSION");
      if (defaultGitDescribeVersion != null) {
        if (defaultGitDescribeVersion.startsWith("v")) {
          return defaultGitDescribeVersion.substring(1);
        } else {
          throw new GradleException("DEFAULT_GIT_DESCRIBE_VERSION must start with 'v'");
        }
      }
    }

    return null;
  }
}
