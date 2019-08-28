import pl.blueenergy.document.DocumentDao;
import pl.blueenergy.organization.User;
import pl.blueenergy.utils.FileExporter;
import pl.blueenergy.utils.QuestionnaireFileExporter;
import pl.blueenergy.utils.UserSalaryModifier;

import java.util.Random;

public class Main {
	
	public static void main(String[] args) {
		DocumentDao documentDao = new DocumentDao();
		ProgrammerService programmerService = new ProgrammerService(documentDao);
		programmerService.execute();
		Integer randomQuestionnaryIndex = new Random().nextInt(programmerService.getAllQuestionnaires().size());
		Integer randomUserIndex = new Random().nextInt(programmerService.getAllUsers().size());
		FileExporter fileExporter = new QuestionnaireFileExporter(programmerService.getAllQuestionnaires().get(randomQuestionnaryIndex));
		fileExporter.export("Questionnary.txt");
		User user = programmerService.getAllUsers().get(randomUserIndex);
		UserSalaryModifier userSalaryModifier = new UserSalaryModifier(user);
		userSalaryModifier.changeSalary(1000);
	}
}
