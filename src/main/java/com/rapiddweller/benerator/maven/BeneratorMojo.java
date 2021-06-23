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

import com.rapiddweller.benerator.main.Benerator;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.IOException;

/**
 * Executes benerator using the specified descriptor file. Invoked by <code>mvn benerator:generate</code>.<br/>
 * <br/>
 * Created: 10.07.2008 06:40:23
 *
 * @author Volker Bergmann
 * @goal generate
 * @since 0.5.4
 */
public class BeneratorMojo extends AbstractBeneratorMojo {

	/**
	 * The fully qualified name of the JDBC database driver (can be queried in the descriptor file as ${db_driver}).
	 *
	 * @parameter
	 */
	protected String dbDriver;

	/**
	 * The JDBC database url (can be queried in the descriptor file as ${db_url}).
	 *
	 * @parameter
	 */
	protected String dbUrl;

	/**
	 * The database user name (can be queried in the descriptor file as ${db_user}).
	 *
	 * @parameter expression="${user.name}"
	 */
	protected String dbUser;

	/**
	 * The database password (can be queried in the descriptor file as ${db_password}).
	 *
	 * @parameter expression="${user.name}"
	 */
	protected String dbPassword;

	/**
	 * The database schema to use (can be queried in the descriptor file as ${db_schema}).
	 *
	 * @parameter expression="${user.name}"
	 */
	protected String dbSchema;

	/**
	 * The path of the descriptor file relative to the project's root.
	 * This defaults to <code>src/test/benerator/benerator.ben.xml</code>.
	 *
	 * @parameter default-value="benerator.xml"
	 */
	private File descriptor;

	/**
	 * Specifies if maven should perform internal validation.
	 * This defaults to <code>true</code>.
	 *
	 * @parameter default-value="true"
	 */
	private boolean validate;

	/**
	 * Invokes benerator using the settings from the pom's configuration.
	 */
	public void execute() throws MojoExecutionException {
		setSystemProperties();
		setupClasspath();
		try {
			Benerator.main(new String[] {descriptor.getAbsolutePath()});
		} catch (IOException e) {
			throw new MojoExecutionException("Error in generation", e);
		}
	}

	// non-public helpers ----------------------------------------------------------------------------------------------

	@Override
	protected void setSystemProperties() {
		super.setSystemProperties();
		setSystemProperty("db_driver", dbDriver);
		setSystemProperty("dbDriver", dbDriver);

		setSystemProperty("db_url", dbUrl);
		setSystemProperty("dbUrl", dbUrl);

		setSystemProperty("db_user", dbUser);
		setSystemProperty("dbUser", dbUser);

		setSystemProperty("db_password", dbPassword);
		setSystemProperty("dbPassword", dbPassword);

		setSystemProperty("db_schema", dbSchema);
		setSystemProperty("dbSchema", dbSchema);

		System.setProperty("file.encoding", encoding);
		System.setProperty("benerator.validate", String.valueOf(validate));
	}

}
