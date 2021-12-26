# Installation Guide

Steps on how to install,deploy and run the drone project.
### Step one

- Download and Install mysql
  - Version **8.0.20**
  - [Download here](https://dev.mysql.com/downloads/installer/)
- Creat a database called it  thedrone or rather you can choose any name as long it  follows naming convention standards.

### Step Two

- Setup the application server
  - Download and install wildfly 
       - Version wildfly **25.0.1.Final**
       - [Download here](https://wildfly.org/downloads/)
  - Add mysql-connector jar file for the respective version of mysql(8.0.20) that you install to your application server
    - For wildfly go to (/wildfly-18.0.0.Final/modules/com/mysql/main) and add my the jar file. If folders (/com/mysql/main)  does not exits please create.
    - Also in the above folder (com/mysql/main) add xml  file called module.xml and add the following

```xml
         <?xml version="1.0" encoding="UTF-8"?>
         <module xmlns="urn:jboss:module:1.0" name="com.mysql">
           <resources>
             <resource-root path="mysql-connector-java-8.0.20-bin.jar"/>
           </resources>
           <dependencies>
             <module name="javax.api"/>
           </dependencies>
           </module>
```

- Replace mysql-connector-java-8.0.20-bin.jar with the mysql-connector you just added.
  - Open application server and go to standalone.xml file. Location (/wildfly-18.0.0.Fina/standalone/configuration).
        -Add new datasource and also mysql driver. Datasource name should be **java:/thedroneDS** . Below is how it should look like:

```xml
                <datasource jta="false" jndi-name="java:/thedroneDS" pool-name="Mss" enabled="true" use-ccm="false">
                                    <connection-url>jdbc:mysql://localhost:3306/thedrone</connection-url>
                                    <driver-class>com.mysql.jdbc.Driver</driver-class>
                                    <driver>com.mysql</driver>
                                    <security>
                                        <user-name>*****</user-name>
                                        <password>*****</password>
                                    </security>
                                    <validation>
                                        <validate-on-match>false</validate-on-match>
                                        <background-validation>false</background-validation>
                                    </validation>
                                    <statement>
                                        <share-prepared-statements>false</share-prepared-statements>
                                    </statement>
                                </datasource>
```

```xml
                    <driver name="com.mysql" module="com.mysql">
                        <xa-datasource-class>com.mysql.jdbc.jdbc2.optional.MysqlXADataSource</xa-datasource-class>
                    </driver>
```

