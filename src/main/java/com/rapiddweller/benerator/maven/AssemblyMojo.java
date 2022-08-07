/*
 * (c) Copyright 2009 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License.
 *
 * For redistributing this software or a derivative work under a license other
 * than the GPL-compatible Free Software License as defined by the Free
 * Software Foundation or approved by OSI, you must first obtain a commercial
 * license to this software product from Volker Bergmann.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * WITHOUT A WARRANTY OF ANY KIND. ALL EXPRESS OR IMPLIED CONDITIONS,
 * REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE
 * HEREBY EXCLUDED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.rapiddweller.benerator.maven;

import com.rapiddweller.common.FileUtil;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.SystemInfo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Maven mojo that implements the 'benerator:assembly' goal.<br/>
 * <br/>
 * Created at 29.01.2009 14:21:25
 *
 * @author Volker Bergmann
 * @goal assembly
 * @since 0.5.8
 */

public class AssemblyMojo extends AbstractBeneratorMojo {

  public void execute() throws MojoExecutionException {
    try {
      File assemblyDir = new File("target" + File.separator + "assembly");
      copyBeneratorInstallation(assemblyDir);
      copyDependencies(assemblyDir);
      createClasspathFile(assemblyDir);
    } catch (IOException e) {
      throw new MojoExecutionException("Failure in assembly creation", e);
    }
  }

  private void createClasspathFile(File assemblyDir) throws IOException {
    File cpFile = new File(assemblyDir, "classpath.txt"); // TODO v0.6 improve classpath construction
    FileWriter writer = new FileWriter(cpFile);
    try {
      for (String element : getClasspathElements()) {
        File file = new File(element);
        writer.write("LOCALCLASSPATH=$LOCALCLASSPATH:$BENERATOR_HOME/lib/" + file.getName());
        writer.write(SystemInfo.getLineSeparator());
      }
    } finally {
      writer.close();
    }
  }

  private void copyBeneratorInstallation(File assemblyDir) {
    String beneratorHome = System.getenv("BENERATOR_HOME");
    if (StringUtil.isEmpty(beneratorHome)) {
      getLog().error("BENERATOR_HOME not defined");
      return;
    }
    File installationDir = new File(beneratorHome);
    FileUtil.copy(installationDir, assemblyDir, true);
  }

  private void copyDependencies(File assemblyDir)
      throws IOException {
    File libDir = new File(assemblyDir, "lib");
    FileUtil.ensureDirectoryExists(libDir);
    for (String element : getClasspathElements()) {
      File file = new File(element);
      if (file.isFile()) {
        File copy = new File(libDir, file.getName());
        getLog().debug("Copying " + file + " to " + copy);
        FileUtil.copy(file, copy, true);
      }
    }
  }

}
