
rest setup --activatorType APP_CLASS;

repotest setup;

project add-dependency javax.inject:javax.inject:1:provided;

repotest create-test --packages src/main/java/org/adorsys/adph/repo/;

cd ~~;