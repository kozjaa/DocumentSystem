import pl.blueenergy.document.*;
import pl.blueenergy.organization.User;

import java.util.ArrayList;
import java.util.List;

public class ProgrammerService {

	private final DocumentDao documentDao;


	public ProgrammerService(DocumentDao documentDao) {
		this.documentDao = documentDao;
	}

	public List<Questionnaire> getAllQuestionnaires(){
		List<Questionnaire> questionnaires = new ArrayList<>();
		for (Document document : documentDao.getAllDocumentsInDatabase()){
			if (document instanceof Questionnaire){
				questionnaires.add((Questionnaire) document);
			}
		}
		return questionnaires;
	}

	public List<ApplicationForHolidays> getAllApplicationsForHolidays(){
		List<ApplicationForHolidays> applicationForHolidays = new ArrayList<>();
		for (Document document : documentDao.getAllDocumentsInDatabase()){
			if (document instanceof ApplicationForHolidays){
				applicationForHolidays.add((ApplicationForHolidays) document);
			}
		}
		return applicationForHolidays;
	}

	public List<Question> getAllQuestionsFromAllQuestionnaires(){
		List<Question> questions = new ArrayList<>();
		for (Questionnaire questionnaire : getAllQuestionnaires()){
			for (Question question : questionnaire.getQuestions()){
				questions.add(question);
			}
		}
		return questions;
	}

	public double getAverageOfPossibleAnserwsInAllQuestions(){
		Integer questionsNumber = getAllQuestionsFromAllQuestionnaires().size();
		Integer answersNumber = 0;
		for (Question question : getAllQuestionsFromAllQuestionnaires()){
			for (String answer : question.getPossibleAnswers()){
				answersNumber++;
			}
		}
		return answersNumber.doubleValue()/questionsNumber;
	}

	public List<User> getAllUsers(){
		List<User> users = new ArrayList<>();
		for (ApplicationForHolidays applicationForHolidays : getAllApplicationsForHolidays()){
			users.add(applicationForHolidays.getUserWhoRequestAboutHolidays());
		}
		return users;
	}

	public List<String> getInvalidLogins(){
		List<String> logins = new ArrayList<>();
		for (User user : getAllUsers()){
			if (!user.getLogin().matches("\\w+")){
				logins.add(user.getLogin());
			}
		}
		return logins;
	}

	public List<ApplicationForHolidays> getApplicationsForHolidayWithInvalidDates(){
		List<ApplicationForHolidays> applicationForHolidays = new ArrayList<>();
		for(ApplicationForHolidays application : getAllApplicationsForHolidays()){
			if (application.getSince().after(application.getTo())){
				applicationForHolidays.add(application);
			}
		}
		return applicationForHolidays;
	}

	public void execute() {
		System.out.println("Średnia możliwych odpowiedzi dla wszystkich pytań: " + getAverageOfPossibleAnserwsInAllQuestions());
		System.out.println();
		System.out.println("Lista loginów zawierająca polskie znaki: " + getInvalidLogins());
		System.out.println();
		System.out.println("Lista wniosków urlopowych zawierających niepoprawną kolejność dat: " + getApplicationsForHolidayWithInvalidDates());
	}
}
