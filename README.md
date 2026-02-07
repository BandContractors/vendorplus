# VendorPlus

VendorPlus is a Java Server Faces (JSF) web application for POS and business operations. It runs on Apache Tomcat and uses MySQL with a JNDI datasource.

## Highlights
- JSF UI with PrimeFaces components
- Ant-based build (build.xml)
- MySQL backend with tables, views, and stored procedures
- JNDI datasource: jdbc/BranchPool

## Project Structure
- web/: JSF pages, static assets, styles
- src/java/: Java source (beans, entities, utilities)
- lib/: Libraries
- build.xml: Ant build
- dist/: WAR output (vendorplus.war)
- vp/: Database dumps and scripts

## Runtime Requirements
- Java 8 (JDK/JRE)
- Apache Tomcat 8
- MySQL 5.7+

## Quick Start (Local)
1) Start MySQL and note the port (example: 3307).
2) Import database, views, and stored procedures.
3) Configure Tomcat JNDI datasource.
4) Deploy the WAR or copy the webapp to Tomcat.
5) Restart Tomcat and open the app.

## Database Setup
Use the MySQL client that ships with WampServer. Example path:

```
C:\wamp64\bin\mysql\mysql5.7.26\bin\mysql.exe
```

Create and import:

```
mysql -u root -P 3307 -e "CREATE DATABASE IF NOT EXISTS vendorplus;"
mysql -u root -P 3307 vendorplus < vp\vendorplus.sql
mysql -u root -P 3307 vendorplus < vp\stored_procedures.sql
mysql -u root -P 3307 vendorplus < vp\views.sql
```

Verify:

```
mysql -u root -P 3307 -e "SHOW FULL TABLES IN vendorplus WHERE Table_type='VIEW';"
mysql -u root -P 3307 -e "SHOW PROCEDURE STATUS WHERE Db='vendorplus';"
```

## Tomcat Datasource (JNDI)
The app uses a JNDI datasource called jdbc/BranchPool configured in:
- web/META-INF/context.xml

Example configuration:

```
url=jdbc:mysql://localhost:3307/vendorplus?autoReconnect=true
username=root
password=(empty if no password)
```

If you change the MySQL port, update the URL above and restart Tomcat.

## Configuration File
Config file:
- src/java/configurations/ConfigFile.properties

Fields:
- branch_host
- branch_database
- branch_user
- branch_password

This file is used by app components, but runtime DB connections are typically done via the JNDI datasource.

## Build
Ant build script:
- build.xml

Typical build (from project root):

```
ant clean
ant dist
```

This produces dist/vendorplus.war.

## Deploy
Option A: WAR deploy
1) Copy dist/vendorplus.war to Tomcat webapps.
2) Ensure context.xml is present in webapps/vendorplus/META-INF.
3) Restart Tomcat.

Option B: Exploded deploy
1) Copy web/ to webapps/vendorplus.
2) Copy WEB-INF/classes and WEB-INF/lib from build output if needed.
3) Restart Tomcat.

## Local URLs
- App root: http://localhost:8080/vendorplus/
- Home: http://localhost:8080/vendorplus/faces/Home.xhtml

## UI and Theme
Theme colors are defined in web/style.css and inline styles in xhtml files.
Current brand colors:
- Primary green: #7BC043
- Primary blue: #1F3A5F

Favicon (global):
- web/resources/images/arrow.png

## Home Page
Home page layout is defined in:
- web/Home.xhtml

It includes quick actions and a hero image background using vendorplus.jpeg.

## System Name and Version
Shown values are pulled from parameter_list:

```
SELECT context, parameter_name, parameter_value
FROM parameter_list
WHERE context='SYSTEM' AND parameter_name IN ('SYSTEM_NAME','SYSTEM_VERSION');
```

Update example:

```
UPDATE parameter_list
SET parameter_value='VENDORPLUS'
WHERE context='SYSTEM' AND parameter_name='SYSTEM_NAME';
```

## Logo Configuration
Logo path is stored in company_setting.logo_url and read by companySetting.getLogoUrl().

Example update:

```
UPDATE company_setting
SET logo_url='resources/images/arrow.png'
WHERE company_setting_id=1;
```

## License Checks
Many pages call:

```
#{navigationBean.checkLicenseExpired()}
```

If the license is expired, the app blocks access and shows a message.
The license key is stored in:

```
company_setting.license_key
```

Provide a valid license key to the system vendor and update this field.

## Troubleshooting
Database connection errors
- Verify MySQL is running and the port matches context.xml.
- Check Tomcat logs in Tomcat/logs/ (catalina.*.log).

Blank header values
- Ensure parameter_list contains SYSTEM_NAME and SYSTEM_VERSION.
- Confirm DB connection is healthy.

Logo not showing
- Ensure company_setting.logo_url points to a valid resource path.
- Confirm the image exists in web/resources/images.

License expired blocks pages
- Update company_setting.license_key with a valid key.

## Notes
- Runtime DB access is via JNDI. ConfigFile.properties is still used for some components.
- Always restart Tomcat after changing context.xml or datasource settings.
