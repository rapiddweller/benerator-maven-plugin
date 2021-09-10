/*
 * (c) Copyright 2008-2009 by Volker Bergmann. All rights reserved.
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

import com.rapiddweller.common.CollectionUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Common parent class for all Benerator Mojos. <br/>
 * Created: 10.07.2008 16:21:23
 *
 * @author Volker Bergmann
 * @requiresDependencyResolution test
 * @since 0.5.4
 */
public abstract class AbstractBeneratorMojo extends AbstractMojo {

  // attributes to be mapped from the pom's plugin configuration -----------------------------------------------------

  /**
   * The file encoding to use as default for all file I/O. It defaults to the
   * system's encoding.
   *
   * @parameter expression="${file.encoding}"
   */
  protected String encoding;

  /**
   * The scope of which dependencies should be resolved.
   *
   * @parameter default="runtime"
   */
  protected String scope;

  /**
   * Runtime classpath elements.
   *
   * @parameter expression="${project.runtimeClasspathElements}"
   * @required
   * @readonly
   */
  protected List<String> runtimeClasspathElements;

  /**
   * Test classpath elements.
   *
   * @parameter expression="${project.testClasspathElements}"
   * @required
   * @readonly
   */
  protected List<String> testClasspathElements;

  // convenience methods for child classes ---------------------------------------------------------------------------

  /**
   * Maps all attributes to System properties of the same name.
   */
  protected void setSystemProperties() {
    setSystemProperty("file.encoding", encoding);
  }

  // private helpers -------------------------------------------------------------------------------------------------

  /**
   * Sets a single System property.
   */
  protected void setSystemProperty(String name, String value) {
    // getLog().info(name + ": " + value);
    if (value != null) {
      System.setProperty(name, value);
    }
  }

  protected void setupClasspath() throws MojoExecutionException {
    try {
      List<String> classpathElements = getClasspathElements();
      ClassLoader parentClassLoader = getClass().getClassLoader();
      ClassLoader classLoader = new URLClassLoader(getClasspathURLs(), parentClassLoader);
      Thread.currentThread().setContextClassLoader(classLoader);
      getLog().debug("Classpath elements: " + classpathElements);
    } catch (IOException e) {
      throw new MojoExecutionException("Error in generation", e);
    }
  }

  protected List<String> getClasspathElements() {
    return ("test".equals(scope) ? testClasspathElements : runtimeClasspathElements);
  }

  protected URL[] getClasspathURLs() throws MalformedURLException {
    List<String> classpathElements = getClasspathElements();
    List<URL> urls = new ArrayList<URL>(classpathElements.size());
    for (String classpathElement : classpathElements) {
      urls.add(new File(classpathElement).toURI().toURL());
    }
    return CollectionUtil.toArray(urls);
  }
}
