
rest setup --activatorType APP_CLASS;

repotest setup;

repotest create-test --packages src/main/java/org/adorsys/adph/repo/;

cd ~~;