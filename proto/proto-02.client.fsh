
new-project --named proto.client --type jar --topLevelPackage org.adorsys.proto.client;

jfx setup;

cd ~~;

jfx ui-create --jpaBasePackage org.adorsys.proto.server --jpaPackage ../proto.server/src/main/java/org/adorsys/proto/server/jpa/;

