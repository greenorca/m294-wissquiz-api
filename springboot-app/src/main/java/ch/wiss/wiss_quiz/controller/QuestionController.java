package ch.wiss.wiss_quiz.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.wiss.wiss_quiz.exception.AnswerCouldNotBeSavedException;
import ch.wiss.wiss_quiz.exception.CategoryNotFoundException;
import ch.wiss.wiss_quiz.exception.QuestionCouldNotBeSavedException;
import ch.wiss.wiss_quiz.exception.QuestionLoadException;
import ch.wiss.wiss_quiz.model.Answer;
import ch.wiss.wiss_quiz.model.AnswerRepository;
import ch.wiss.wiss_quiz.model.Category;
import ch.wiss.wiss_quiz.model.CategoryRepository;
import ch.wiss.wiss_quiz.model.FrontendQuestion;
import ch.wiss.wiss_quiz.model.Question;
import ch.wiss.wiss_quiz.model.QuestionRepository;
import jakarta.validation.Valid;


@RestController // This means that this class is a Controller
@RequestMapping(path = "/questions") // This means URL's start with /question (after Application path)
public class QuestionController {
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private AnswerRepository answerRepository;

	@DeleteMapping(path = "/{question_id}") // Map ONLY POST Requests
	public ResponseEntity<String> deleteQuestion(@PathVariable(value = "question_id") Integer questionId){

		questionRepository.deleteById(questionId);

		return ResponseEntity.ok("Deleted");
	}

	@PostMapping(path = "/{cat_id}/") // Map ONLY POST Requests
	public ResponseEntity<String> addNewQuestion(@PathVariable(value = "cat_id") Integer catId,
			@Valid @RequestBody Question question) {

		Optional<Category> category = categoryRepository.findById(catId);

		if (category.isEmpty()) {
			throw new CategoryNotFoundException(catId);
		}

		question.setCategory(category.get());
		// we need to store nested Answer-Objects separately
		List<Answer> answers = List.copyOf(question.getAnswers());

		question.setAnswers(null);

		try {
			questionRepository.save(question);
		} catch (Exception ex) {
			throw new QuestionCouldNotBeSavedException(question.getQuestion());
		}

		// we need to store nested Answer-Objects separately
		answers.forEach(answer -> {
			answer.setQuestion(question);
			try {
				answerRepository.save(answer);
			} catch (Exception ex) {
				throw new AnswerCouldNotBeSavedException(answer.getAnswer());
			}

		});

		return ResponseEntity.ok("Saved");
	}

	@GetMapping(path = "/")
	public ResponseEntity<Iterable<Question>> getAllQuestions() {
		Iterable<Question> questions = null;
		try {
			questions = questionRepository.findAll();
		} catch (Exception ex) {
			throw new QuestionLoadException();
		}

		return ResponseEntity.ok(questions);
	}

	@GetMapping(path = "/quiz/{cat_id}")
	public ResponseEntity<List<FrontendQuestion>> getQuestionsForQuiz(@PathVariable(value = "cat_id") Integer catId) {

		Optional<Category> category = categoryRepository.findById(catId);
		if (category.isEmpty()) {
			throw new CategoryNotFoundException(catId);
		}
		List<Question> questions = questionRepository.findByCategory(category.get());
		if (questions.isEmpty()) {
			throw new QuestionLoadException(category.get().getId());
		}

		Collections.shuffle(questions);

		List<FrontendQuestion> quizQuestions = questions.subList(0, 3).stream()
			.map(FrontendQuestion::new).toList();

		return ResponseEntity.ok(quizQuestions);
	}
}