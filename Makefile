COMP=javac
EXEC=java

LIBS =junit-console.jar

SRC=src
CLASSES=classes
TESTS=tests

CLASSPATH=$(SRC):$(CLASSES):$(TESTS)

FLAGS= -sourcepath $(SRC) -d $(CLASSES)
TEST_FLAGS = -classpath $(LIBS):$(CLASSES)

SRC_FILES:=$(shell find $(SRC) -not -path "$(SRC)/livrables/*" -name "*.java")
CLASSES_FILES=$(SRC_FILES:$(SRC)/%.java=$(CLASSES)/%.class)
TEST_FILES=$(shell find $(TESTS) -name "*.java")
TEST_FILES_OBJ:=$(TEST_FILES:%.java=%.class)

$(CLASSES)/%.class: $(SRC)/%.java
	$(COMP) $(FLAGS) $<

docs: 
	javadoc -sourcepath $(SRC) -subpackages gameLogic -d docs

classes: $(CLASSES_FILES)

jar: game

game: classes classes/livrables/BloonTD.class game.jar

livrable1: classes livrable1a.jar livrable1b.jar

livrable2: classes livrable2a.jar livrable2b.jar

livrable3: classes livrable3a.jar livrable3b.jar

livrable4: classes livrable4a.jar livrable4b.jar

livrable5: classes classes/livrables/Livrable5.class livrable5.jar 

livrable1a.jar:
	jar cvfm livrable1a.jar manifests/livrable1a-manifest -C classes livrables -C classes gameLogic

livrable1b.jar:
	jar cvfm livrable1b.jar manifests/livrable1b-manifest -C classes livrables -C classes gameLogic

livrable2a.jar:
	jar cvfm $@ manifests/livrable2a-manifest -C classes livrables -C classes gameLogic

livrable2b.jar:
	jar cvfm $@ manifests/livrable2b-manifest -C classes livrables -C classes gameLogic

livrable3a.jar:
	jar cvfm $@ manifests/livrable3a-manifest -C classes livrables -C classes gameLogic

livrable3b.jar:
	jar cvfm $@ manifests/livrable3b-manifest -C classes livrables -C classes gameLogic

livrable4a.jar:
	jar cvfm $@ manifests/livrable4a-manifest -C classes livrables -C classes gameLogic

livrable4b.jar:
	jar cvfm $@ manifests/livrable4b-manifest -C classes livrables -C classes gameLogic

livrable5.jar:
	jar cvfm $@ manifests/livrable5-manifest -C classes livrables -C classes gameLogic -C classes termDisplay

game.jar:
	jar cvfm $@ manifests/game-manifest -C classes livrables -C classes gameLogic -C classes termDisplay

$(TESTS)/%.class : $(TESTS)/%.java
	$(COMP) $(TEST_FLAGS) $<

tests: $(TEST_FILES_OBJ)

livrables_clean:
	rm livrable*.jar game.jar


fclean:
	rm -rf classes/ docs/ report/

clean: livrables_clean fclean 

runtests: tests
	$(EXEC) -jar $(LIBS) -classpath $(CLASSPATH) -scan-classpath

.PHONY: livrables_clean fclean clean
