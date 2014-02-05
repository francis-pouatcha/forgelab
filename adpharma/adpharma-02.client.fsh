
new-project --named adpharma.client --type jar --topLevelPackage org.adorsys.adpharma.client;

jfx setup;

cd ~~;

jfx ui-create --jpaBasePackage org.adorsys.adpharma.server --jpaPackage ../adpharma.server/src/main/java/org/adorsys/adpharma/server/jpa/;

