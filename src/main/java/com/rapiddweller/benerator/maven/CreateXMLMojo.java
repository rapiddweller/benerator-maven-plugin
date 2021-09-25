/*
 * (c) Copyright 2008-2011 by Volker Bergmann. All rights reserved.
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

import com.rapiddweller.benerator.main.XmlCreator;
import com.rapiddweller.common.Assert;
import com.rapiddweller.common.StringUtil;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;

/**
 * Creates XML files from an XML Schema file, supporting XML Schema annotations for generation setup.<br/><br/>
 * Created at 13.07.2008 08:05:11
 *
 * @author Volker Bergmann
 * @goal createxml
 * @since 0.5.4
 */
public class CreateXMLMojo extends AbstractBeneratorMojo {

	/**
	 * The path of the XML Schema file relative to the project's root.
	 *
	 * @parameter property="src/test/benerator/${artifact-id}.xsd"
	 */
	private File xmlSchema;

	/**
	 * The name of the element to use as root for XML file generation.
	 *
	 * @parameter
	 * @required
	 */
	private String xmlRoot;

	/**
	 * The pattern according to which the generated XML files will be named.
	 *
	 * @parameter default-value="file-{0}.xml"
	 */
	private String filenamePattern;

	/**
	 * The number of XML files to generate.
	 *
	 * @parameter default-value="1"
	 */
	private int fileCount;

	/**
	 * A comma-separated list of the files to include as properties files.
	 *
	 * @parameter
	 */
	private String propertiesFiles;

	/**
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	public void execute() throws MojoExecutionException {
		setSystemProperties();
		// check schema file name
		String schemaFilename = xmlSchema.getAbsolutePath();
		Assert.notEmpty(schemaFilename, "No XML schema file specified (in descriptor configuration)");
		// check root
		Assert.notEmpty(xmlRoot, "No root XML element specified (in root configuration)");
		// check property files
		String[] propertiesFiles = StringUtil.tokenize(this.propertiesFiles, ',');
		if (propertiesFiles == null) {
			propertiesFiles = new String[0];
		}
		// run generation
		XmlCreator.createXMLFiles(schemaFilename, xmlRoot, filenamePattern, fileCount, propertiesFiles);
	}

}
