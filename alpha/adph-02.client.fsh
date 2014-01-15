
new-project --named adph.client --type jar --topLevelPackage org.adorsys.adph.client;

jfx setup;

cd ~~;

jfx ui-create --jpaPackagePrefix jpa --javafxPackagePrefix ui --jpaPackage ../adph.server/src/main/java/org/adorsys/adph/server/jpa/
