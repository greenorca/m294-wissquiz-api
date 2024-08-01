package ch.wiss.__wiss_quiz;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ch.wiss.wiss_quiz.controller.QuestionController;
import ch.wiss.wiss_quiz.model.AnswerRepository;
import ch.wiss.wiss_quiz.model.CategoryRepository;
import ch.wiss.wiss_quiz.model.QuestionRepository;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
public class QuestionControllerTest {

	@MockBean
	private QuestionRepository questionRepository;
	@MockBean
	private CategoryRepository categoryRepository;
	@MockBean
	private AnswerRepository answerRepository;

	@Autowired
	QuestionController quizController;

	@Autowired
	private MockMvc mockMvc;

	@Test
    public void whenUserControllerInjected_thenNotNull() throws Exception {
        assertThat(quizController).isNotNull();
    }

	@Test
	public void whenGetRequestToGetAllQuestions_thenCorrectResponse() throws Exception {
	    mockMvc.perform(MockMvcRequestBuilders.get("/question"))
	      .andExpect(MockMvcResultMatchers.status().isOk())
	      .andExpect(MockMvcResultMatchers.content()
	        .contentType(MediaType.APPLICATION_JSON));
	}


	@Test
	public void whenPostRequestToQuestionAndInValidQuestion_thenCorrectResponse() throws Exception {
		String question = "{\"question\": null }";
	    mockMvc.perform(MockMvcRequestBuilders.post("/question/1")
	      .content(question)
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(MockMvcResultMatchers.status().isBadRequest())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.question").value("Question is mandatory"))
	      .andExpect(MockMvcResultMatchers.jsonPath("$.answers").value("There need to be 3 answers to a question"))
	      .andExpect(MockMvcResultMatchers.content()
	        .contentType(MediaType.APPLICATION_JSON));
    }
}