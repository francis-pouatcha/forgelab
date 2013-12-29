
@/* Download and install jboss-eap-6.1 */;
@/* Install these plugins if not done yet. */;
@/* forge install-plugin arquillian */;
@/* forge install-plugin jboss-as-7 */;
@/* forge install-plugin angularjs */;
@/* git clone https://github.com/clovisgakam/forge-envers-plugin.git */;
@/* forge source-plugin forge-envers-plugin */;
@/* git clone https://github.com/francis-pouatcha/forge-repository-plugin.git */;
@/* forge source-plugin forge-repository-plugin */;
@/* git clone https://github.com/francis-pouatcha/javaext.description.git */;
@/* cd javaext.description | mvn clean install | cd .. */;
@/* git clone https://github.com/francis-pouatcha/forge-description-plugin.git */;
@/* forge source-plugin forge-description-plugin */;
@/* run ../adpharma.fsh */;

set ACCEPT_DEFAULTS true;

new-project --named adph --topLevelPackage org.adorsys.adph --finalName adph;

project add-dependency org.hibernate:hibernate-jpamodelgen:1.3.0.Final:provided;
project add-managed-dependency org.jboss.shrinkwrap.resolver:shrinkwrap-resolver-bom:2.0.1:import:pom;
project add-dependency org.jboss.shrinkwrap.resolver:shrinkwrap-resolver-depchain:2.0.1:test:pom;

as7 setup;
persistence setup --provider HIBERNATE --container JBOSS_AS7;

validation setup;

description setup;

project add-dependency junit:junit:4.11:test;

