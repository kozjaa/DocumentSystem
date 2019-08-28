package pl.blueenergy.utils;

import pl.blueenergy.document.Question;
import pl.blueenergy.document.Questionnaire;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class QuestionnaireFileExporter implements FileExporter {

    private final Questionnaire questionnaire;

    public QuestionnaireFileExporter(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    @Override
    public void export(String pathFile) {
        try {
            Files.write(Paths.get(pathFile), getLines(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getLines(){
        List<String> lines = new ArrayList<>();
        for (Question question : questionnaire.getQuestions()){
            int answerNumber = 1;
            lines.add("Pytanie: " + question.getQuestionText());
            for (String answer : question.getPossibleAnswers()){
                lines.add(answerNumber + ". " +answer);
                answerNumber++;
            }
            lines.add("\n");
        }
        return lines;
    }
}
