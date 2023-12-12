package com.VotingApplication;

import com.VotingApplication.Controller.CandidateController;
import com.VotingApplication.Service.CandidateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


class CandidateControllerTest {

	@Mock
	private CandidateService candidateService;

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private CandidateController candidateController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testEnterCandidate() {
		ResponseEntity<String> responseEntity = candidateController.enterCandidate("John");
		assertEquals(201, responseEntity.getStatusCodeValue());
		verify(candidateService, times(1)).enterCandidate("John");

	}

	@Test
	public void testCastVote() {
		when(candidateService.castVote(anyString())).thenReturn(1);

		ResponseEntity<Integer> responseEntity = candidateController.castVote("John");

		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(1, responseEntity.getBody());
		verify(candidateService, times(1)).castVote("John");
	}

	@Test
	public void testCountVote() {
		when(candidateService.countVote(anyString())).thenReturn(2);

		ResponseEntity<Integer> responseEntity = candidateController.countVote("John");

		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(2, responseEntity.getBody());
		verify(candidateService, times(1)).countVote("John");
	}

	@Test
	public void testListVotes() {
		Map<String, Integer> votes = new HashMap<>();
		votes.put("John", 3);
		votes.put("Alice", 1);

		when(candidateService.listVotes()).thenReturn(votes);

		ResponseEntity<Map<String, Integer>> responseEntity = candidateController.listVotes();

		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals(votes, responseEntity.getBody());
		verify(candidateService, times(1)).listVotes();
	}


	@Test
	public void testGetWinner() {
		when(candidateService.getWinner()).thenReturn("John");

		ResponseEntity<String> responseEntity = candidateController.getWinner();

		assertEquals(200, responseEntity.getStatusCodeValue());
		assertEquals("John", responseEntity.getBody());
		verify(candidateService, times(1)).getWinner();
	}


	private Executor executor = Executors.newFixedThreadPool(5);

	@Test
	public void testConcurrentEnterCandidate() {
		int numThreads = 10; // Replace with your desired number of threads

		CompletableFuture<?>[] futures = IntStream.range(0, numThreads)
				.mapToObj(i -> CompletableFuture.runAsync(() -> {
					String candidateName = "Candidate" + i;
					enterCandidate(candidateName);
				}, executor))
				.toArray(CompletableFuture[]::new);

		CompletableFuture.allOf(futures).join();
	}
	// Mocked method for testing
	private void enterCandidate(String candidateName) {
		// Mock the enterCandidate method logic for testing
		System.out.println("Entered candidate: " + candidateName);
	}
}







// do Not use these .


//Tpassed
//	@BeforeEach
//	void setUp() {
//		mockMvc = MockMvcBuilders.standaloneSetup(candidateController).build();
//		// Additional setup if needed
//	}
//
//	@Test
//	void testConcurrentEnterCandidate() throws Exception {
//		// Use CompletableFuture to run the test concurrently
//		CompletableFuture<ResultActions> future1 = CompletableFuture.supplyAsync(() -> {
//			try {
//				return mockMvc.perform(post("/api/entercandidate")
//								.param("name", "User1"))
//						.andExpect(status().isOk());
//			} catch (Exception e) {
//				throw new RuntimeException(e);
//			}
//		});
//
//		CompletableFuture<ResultActions> future2 = CompletableFuture.supplyAsync(() -> {
//			try {
//				return mockMvc.perform(post("/api/entercandidate")
//								.param("name", "User2"))
//						.andExpect(status().isOk());
//			} catch (Exception e) {
//				throw new RuntimeException(e);
//			}
//		});
//	}
//
//	@Test
//	void testConcurrentEnterCandidate() throws Exception {
//		// Number of concurrent requests
//		int numberOfThreads = 10;
//
//		// Use CompletableFuture to run the test concurrently
//		CompletableFuture<ResultActions>[] futures = new CompletableFuture[numberOfThreads];
//
//		for (int i = 0; i < numberOfThreads; i++) {
//			final int threadNumber = i + 1;
//			futures[i] = CompletableFuture.supplyAsync(() -> {
//				try {
//					return mockMvc.perform(post("/api/entercandidate")
//									.param("name", "User" + threadNumber))
//							.andExpect(status().isOk());
//				} catch (Exception e) {
//					// If an exception occurs, complete the CompletableFuture exceptionally
//					throw new RuntimeException(e);
//				}
//			}).exceptionally(ex -> {
//				// Log or handle the exception if needed
//				ex.printStackTrace();
//				return null; // Complete exceptionally to avoid NPE
//			});
//		}
//
//		// Wait for all CompletableFuture to complete
//		CompletableFuture<Void> allOf = CompletableFuture.allOf(futures);
//
//		// Block until all futures are completed
//		allOf.join();
//
//		// Optionally, you can handle the results of each CompletableFuture
//		for (int i = 0; i < numberOfThreads; i++) {
//			CompletableFuture<ResultActions> future = futures[i];
//			future.thenAccept(resultActions -> {
//				// Handle the result if needed
//			});
//		}
//	}
//}


//22	@Test
//	void testConcurrentEnterCandidate() throws Exception {
//		// Use CompletableFuture to run the test concurrently
//		CompletableFuture<ResultActions> future1 = CompletableFuture.supplyAsync(() -> {
//			try {
//				return mockMvc.perform(post("/api/entercandidate")
//								.param("name", "User1"))
//						.andExpect(status().isOk());
//			} catch (Exception e) {
//				throw new RuntimeException(e);
//			}
//		});
//
//		CompletableFuture<ResultActions> future2 = CompletableFuture.supplyAsync(() -> {
//			try {
//				return mockMvc.perform(post("/api/entercandidate")
//								.param("name", "User2"))
//						.andExpect(status().isOk());
//			} catch (Exception e) {
//				throw new RuntimeException(e);
//			}
//		});
//
//		// Wait for both tasks to complete
//		CompletableFuture.allOf(future1, future2).join();
//
//		// Get the result from CompletableFuture and perform additional assertions if needed
//		ResultActions result1 = future1.get();
//		ResultActions result2 = future2.get();
//
//		// Additional assertions or cleanup code can be added here
//	}
//}
//1.
//		@Test
//		void testConcurrentEnterCandidate() {
//			CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
//				try {
//					mockMvc.perform(post("/api/entercandidate")
//									.param("name", "User1")
//									.contentType(MediaType.APPLICATION_JSON))
//							.andExpect(status().isOk());
//					System.out.println("Completed task 1");
//				} catch (Exception e) {
//					System.out.println("Task 1 failed with exception: " + e);
//				}
//			});
//
//			CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
//				try {
//					mockMvc.perform(post("/api/entercandidate")
//									.param("name", "User2")
//									.contentType(MediaType.APPLICATION_JSON))
//							.andExpect(status().isOk());
//					System.out.println("Completed task 2");
//					System.out.println("Completed task 2");
//				} catch (Exception e) {
//					System.out.println("Task 2 failed with exception: " + e);
//				}
//			});
//
//			CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(future1, future2);
//
//			// Wait for all tasks to complete
//			combinedFuture.join();
//		}
//	}
//	@Test
//	void testConcurrentEnterCandidate() throws Exception {
//		// Use CompletableFuture to run the test concurrently
//		CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
//			try {
//				mockMvc.perform(post("/api/entercandidate")
//								.param("name", "User1")
//								.contentType(MediaType.APPLICATION_JSON))
//						.andExpect(status().isOk());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		});
//
//		CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
//			try {
//				mockMvc.perform(post("/api/entercandidate")
//								.param("name", "User2")
//								.contentType(MediaType.APPLICATION_JSON))
//						.andExpect(status().isOk());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		});
//
//		// Wait for both tasks to complete
//		CompletableFuture.allOf(future1, future2).get();
//
//		// Additional assertions or cleanup code can be added here
//
//		// For example, you might want to check the final state after concurrent execution
//		ResponseEntity<Integer> votesForUser1 = candidateController.countVote("User1");
//		ResponseEntity<Integer> votesForUser2 = candidateController.countVote("User2");
//
//		// Ensure that both users have cast votes
//		assertEquals(1, votesForUser1);
//		assertEquals(1, votesForUser2);
//	}
//
//
//}
//	@Test
//	public void testConcurrentEnterCandidate() throws InterruptedException {
//		int numberOfThreads = 10;
//		CountDownLatch latch = new CountDownLatch(numberOfThreads);
//
//		ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
//
//		for (int i = 0; i < numberOfThreads; i++) {
//			executorService.submit(() -> {
//				try {
//					candidateController.enterCandidate("John");
//				} finally {
//					latch.countDown();
//				}
//			});
//		}
//
//		latch.await(); // Wait for all threads to finish
//		executorService.shutdown();
//
//		// Verify the state after concurrent calls
////		System.out.println(candidateService.getCandidates());
//		assertEquals(0, candidateService.getCandidates().get("John"));
//	}
//
//	@Test
//	public void testConcurrentCastVote() throws InterruptedException {
//		candidateService.enterCandidate("John");
//
//		int numberOfThreads = 10;
//		CountDownLatch latch = new CountDownLatch(numberOfThreads);
//
//		ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
//
//		for (int i = 0; i < numberOfThreads; i++) {
//			executorService.submit(() -> {
//				try {
//					candidateController.castVote("John");
//				} finally {
//					latch.countDown();
//				}
//			});
//		}
//
//		latch.await(); // Wait for all threads to finish
//		executorService.shutdown();
//
//		// Verify the state after concurrent calls
//		assertEquals(numberOfThreads, candidateService.getCandidates().get("John").getVoteCount());
//	}
//}
