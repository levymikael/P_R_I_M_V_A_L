VERSION = $(shell grep "APPLICATION_VERSION" "define.h" | cut -d ' ' -f 3 | cut -d '"' -f 2)
APP_NAME = Primval
APP_DIR = ..
SETUP_DIR = .
JAVA_FILES = $(shell find ${APP_DIR} -name "*.java")
LAUNCHER_DIR = ${SETUP_DIR}/Launcher
PROG_DIR = ${SETUP_DIR}/Prog
RELEASE_DIR = ${SETUP_DIR}/Release
SETUP_NAME = ${PREFIX}Setup${APP_NAME}
UPDATE_NAME = ${PREFIX}Update${APP_NAME}

clean:
	rm ${APP_DIR}/desktop/build/libs/desktop-1.0.jar

build: ${APP_DIR}/desktop/build/libs/desktop-1.0.jar
prog: ${PROG_DIR}/${APP_NAME}.jar
launcher: ${LAUNCHER_DIR}/${APP_NAME}.exe
setup: ${RELEASE_DIR}/${VERSION}/${SETUP_NAME}.exe
update: ${RELEASE_DIR}/${VERSION}/${UPDATE_NAME}.exe

push-setup: ${RELEASE_DIR}/${VERSION}/${SETUP_NAME}.exe
	curl -F 'file=@${RELEASE_DIR}/${VERSION}/${SETUP_NAME}.exe' -F 'version=${VERSION}' https://download.primval.app/push.php

push-update: ${RELEASE_DIR}/${VERSION}/${UPDATE_NAME}.exe
	curl -F 'file=@${RELEASE_DIR}/${VERSION}/${UPDATE_NAME}.exe' -F 'version=${VERSION}' https://download.primval.app/push.php

${APP_DIR}/desktop/build/libs/desktop-1.0.jar: ${JAVA_FILES}
	cd ${APP_DIR} && ./gradlew Desktop:dist

${PROG_DIR}/${APP_NAME}.jar: ${APP_DIR}/desktop/build/libs/desktop-1.0.jar
	rm -f ${PROG_DIR}/${APP_NAME}.jar
	cp ${APP_DIR}/desktop/build/libs/desktop-1.0.jar ${PROG_DIR}/
	mv ${PROG_DIR}/desktop-1.0.jar ${PROG_DIR}/${APP_NAME}.jar

${LAUNCHER_DIR}/${APP_NAME}.exe: ${LAUNCHER_DIR}/${APP_NAME}.xml
	launch4jc ${LAUNCHER_DIR}/${APP_NAME}.xml

${RELEASE_DIR}/${VERSION}/${SETUP_NAME}.exe: ${LAUNCHER_DIR}/${APP_NAME}.exe ${PROG_DIR}/${APP_NAME}.jar ${SETUP_DIR}/Setup.iss
	mkdir -p ${RELEASE_DIR}/${VERSION}
	iscc ${SETUP_DIR}/Setup.iss

${RELEASE_DIR}/${VERSION}/${UPDATE_NAME}.exe: ${LAUNCHER_DIR}/${APP_NAME}.exe ${PROG_DIR}/${APP_NAME}.jar ${SETUP_DIR}/Update.iss
	mkdir -p ${RELEASE_DIR}/${VERSION}
	iscc ${SETUP_DIR}/Update.iss
