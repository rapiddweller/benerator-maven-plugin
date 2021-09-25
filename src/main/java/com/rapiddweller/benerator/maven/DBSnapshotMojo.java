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

import com.rapiddweller.benerator.main.DBSnapshotTool;
import com.rapiddweller.common.Assert;
import com.rapiddweller.common.StringUtil;

/**
 * Creates a database snapshot in DbUnit data file format.<br/>
 * <br/>
 * Created: 09.07.2008 18:50:23
 *
 * @author Volker Bergmann
 * @goal dbsnapshot
 * @since 0.5.4
 */
public class DBSnapshotMojo extends AbstractBeneratorMojo {

  /**
   * The fully qualified name of the JDBC database driver.
   *
   * @parameter
   * @required
   */
  protected String dbDriver;

  /**
   * The JDBC database url.
   *
   * @parameter
   * @required
   */
  protected String dbUrl;

  /**
   * The database user name.
   *
   * @parameter property="user.name"
   * @required
   */
  protected String dbUser;

  /**
   * The database password.
   *
   * @parameter property="user.name"
   */
  protected String dbPassword;

  /**
   * The database catalog to use.
   *
   * @parameter property="user.name"
   */
  protected String dbCatalog;

  /**
   * The database schema to use.
   *
   * @parameter property="user.name"
   */
  protected String dbSchema;

  /**
   * The file format to use in the export file.
   * Available values: dbunit, sql, xls.
   * If left blank, dbunit is used.
   *
   * @parameter property="user.name"
   */
  protected String snapshotFormat;

  /**
   * The database dialect to use in a snapshot file of SQL format.
   * If left blank, the database's own dialect will be used.
   *
   * @parameter
   */
  protected String snapshotDialect;

  /**
   * The file name to use for the snapshot file.
   * If left blank, 'export' + file type suffix is used.
   *
   * @parameter
   */
  protected String snapshotFilename;

  @Override
  protected void setSystemProperties() {
    super.setSystemProperties();
    setSystemProperty("dbDriver", dbDriver);
    setSystemProperty("dbUrl", dbUrl);
    setSystemProperty("dbUser", dbUser);
    setSystemProperty("dbPassword", dbPassword);
    setSystemProperty("dbCatalog", dbCatalog);
    setSystemProperty("dbSchema", dbSchema);
  }

  /**
   * 'Main' method of the Mojo which calls the DbSnapshotTool using the pom's benerator configuration.
   */
  public void execute() {
    getLog().info(getClass().getName());
    setSystemProperties();
    Assert.notNull(dbUrl, "dbUrl");
    Assert.notNull(dbDriver, "dbDriver");
    Assert.notNull(dbUser, "dbUser");
    // check format
    if (StringUtil.isEmpty(snapshotFormat)) {
      snapshotFormat = DBSnapshotTool.DBUNIT_FORMAT;
    }
    // check file name
    if (StringUtil.isEmpty(snapshotFilename)) {
      if (DBSnapshotTool.DBUNIT_FORMAT.equals(snapshotFormat)) {
        snapshotFilename = "snapshot.dbunit.xml";
      } else {
        snapshotFilename = "snapshot." + snapshotFormat;
      }
    }
    DBSnapshotTool.export(dbUrl, dbDriver, dbCatalog, dbSchema, dbUser, dbPassword,
        snapshotFilename, snapshotFormat, snapshotDialect);
  }

}
