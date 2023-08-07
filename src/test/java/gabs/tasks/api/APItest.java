package gabs.tasks.api;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APItest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://193.123.103.197:8001/tasks-backend/";
	}
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200)
		;
	}
	
	@Test
	public void deveAdicionarTarefasComSucesso() {
		RestAssured.given()
			.contentType(ContentType.JSON)
			.body("{\"task\": \"testezin via api\",\"dueDate\": \"2023-12-21\"}")
		.when()
			.post("/todo")
		.then()
			.statusCode(201)
		;
	}
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		RestAssured.given()
			.contentType(ContentType.JSON)
			.body("{\"task\": \"testezin via api\",\"dueDate\": \"2020-12-21\"}")
		.when()
			.post("/todo")
		.then()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))
		;
	}
	@Test
	public void deveRemoverTarefasComSucesso() {
		//inserir tarefa
		Integer id = RestAssured.given()
			.contentType(ContentType.JSON)
			.body("{\"task\": \"testezin via api\",\"dueDate\": \"2023-12-21\"}")
		.when()
			.post("/todo")
		.then()
		//	.log().all()
			.statusCode(201)
			.extract().path("id");
		System.out.println(id);
		//Remover
		RestAssured.given()
		.when()
			.delete("/todo/"+id)
		.then()
			.statusCode(204);
	}
}
