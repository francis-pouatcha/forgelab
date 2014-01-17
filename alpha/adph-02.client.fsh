
new-project --named adph.client --type jar --topLevelPackage org.adorsys.adph;

jfx setup;

cd ~~;

<<<<<<< HEAD
jfx ui-create --jpaPackagePrefix jpa --javafxPackagePrefix ui --jpaPackage ../adph.server/src/main/java/org/adorsys/adph/server/jpa/
=======
jfx ui-create --jpaBasePackage org.adorsys.adph.server --jpaPackage ../adph.server/src/main/java/org/adorsys/adph/server/jpa/
>>>>>>> 619d5d89ee7700bb64c951a7615494c87ef349db
