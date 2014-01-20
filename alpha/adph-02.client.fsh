
new-project --named adph.client --type jar --topLevelPackage org.adorsys.adph;

jfx setup;

cd ~~;

jfx ui-create --jpaBasePackage org.adorsys.adph.server --jpaPackage ../adph.server/src/main/java/org/adorsys/adph/server/jpa/
