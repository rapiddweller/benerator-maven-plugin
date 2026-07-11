/*
 * (c) Copyright 2026 by rapiddweller GmbH. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License.
 *
 * For redistributing this software or a derivative work under a license other
 * than the GPL-compatible Free Software License as defined by the Free
 * Software Foundation or approved by OSI, you must first obtain a commercial
 * license to this software product from rapiddweller GmbH.
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

import com.rapiddweller.benerator.main.datamimic.DatamimicConverter;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;

/**
 * Converts Benerator XML descriptors (*.ben.xml) below {@link #sourceDirectory} to DATAMIMIC format,
 * writing the result (plus a migration-summary.md) to {@link #outputDirectory}.
 * Invoked by <code>mvn benerator:datamimic</code>.
 *
 * @goal datamimic
 * @since 4.0.0
 */
public class DatamimicConvertMojo extends AbstractBeneratorMojo {

  /**
   * The Benerator descriptor file, or directory searched recursively for *.ben.xml files, to convert.
   *
   * @parameter default-value="src/test/benerator"
   */
  private File sourceDirectory;

  /**
   * The directory the converted DATAMIMIC descriptors and migration-summary.md are written to.
   *
   * @parameter default-value="${project.build.directory}/datamimic"
   */
  private File outputDirectory;

  /**
   * Optional file to additionally write the detailed per-item migration report to.
   *
   * @parameter
   */
  private File reportFile;

  /**
   * @see org.apache.maven.plugin.Mojo#execute()
   */
  public void execute() throws MojoExecutionException {
    setSystemProperties();
    String[] args = (reportFile != null)
        ? new String[] {sourceDirectory.getAbsolutePath(), outputDirectory.getAbsolutePath(), reportFile.getAbsolutePath()}
        : new String[] {sourceDirectory.getAbsolutePath(), outputDirectory.getAbsolutePath()};
    try {
      DatamimicConverter.main(args);
    } catch (Exception e) {
      throw new MojoExecutionException("Error converting descriptors to DATAMIMIC format", e);
    }
  }

}
