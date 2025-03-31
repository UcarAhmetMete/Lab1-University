package university;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {

// R1
	/**
	 * Constructor
	 * @param name name of the university
	 */
	private String name;
	private String RectorName;
	private String RectorSurname;
	private int NextStudentId = 10000;
	private HashMap<Integer, String> students = new HashMap<>();
	private HashMap<Integer, String> courses = new HashMap<>();
	private HashMap<Integer, List<Integer>> registered = new HashMap<>(); // Students -> Course
	private HashMap<Integer, List<Integer>> courseRegistrations = new HashMap<>(); // Course -> Students
	private HashMap<Integer, HashMap<Integer, Integer>> grades = new HashMap<>(); // For the exam() methods helps to find

	private int NextCourseCode = 10;

	public University(String name){
		// Example of logging
		// logger.info("Creating extended university object");
		//TODO: to be implemented
		this.name = name; 
		logger.info("University"+ name + "created");
	}
	
	/**
	 * Getter for the name of the university
	 * 
	 * @return name of university
	 */
	public String getName(){
		//TODO: to be implemented
		return name;
	}
	
	/**
	 * Defines the rector for the university
	 * 
	 * @param first first name of the rector
	 * @param last	last name of the rector
	 */
	public void setRector(String first, String last){
		//TODO: to be implemented
		this.RectorName = first;
		this.RectorSurname= last;
	}
	
	/**
	 * Retrieves the rector of the university with the format "First Last"
	 * 
	 * @return name of the rector
	 */
	public String getRector(){
		//TODO: to be implemented
		return RectorName + " " + RectorSurname;
	}
	
// R2
	/**
	 * Enrol a student in the university
	 * The university assigns ID numbers 
	 * progressively from number 10000.
	 * 
	 * @param first first name of the student
	 * @param last last name of the student
	 * 
	 * @return unique ID of the newly enrolled student
	 */
	
	public int enroll(String first, String last){
		//TODO: to be implemented
		int StudentID = NextStudentId++; // It increments the number for every other students.
		students.put(StudentID,first + " " + last); // This a method of HashMap basicly firs parameter is key and other is the value
		return StudentID; // returns student number

	}
	
	/**
	 * Retrieves the information for a given student.
	 * The university assigns IDs progressively starting from 10000
	 * 
	 * @param id the ID of the student
	 * 
	 * @return information about the student
	 */
	public String student(int id){
		if(students.containsKey(id)){
			return id + " " + students.get(id);
		}
		else{
			return "Student not Found!!";
		}
	}
	
// R3
	/**
	 * Activates a new course with the given teacher
	 * Course codes are assigned progressively starting from 10.
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * 
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher){
		//TODO: to be implemented
		int CourseCode = NextCourseCode++; // Assigned and Incremented.
		courses.put(CourseCode, title + " , " + teacher); // I used hash map key-value relation again. So, CourseID will be maps to title and teacher.Complexity is O(1) for HASHMAP
		return CourseCode;
	}
	
	/**
	 * Retrieve the information for a given course.
	 * 
	 * The course information is formatted as a string containing 
	 * code, title, and teacher separated by commas, 
	 * e.g., {@code "10,Object Oriented Programming,James Gosling"}.
	 * 
	 * @param code unique code of the course
	 * 
	 * @return information about the course
	 */
	public String course(int code) {
		if (courses.containsKey(code)) { //.cointainsKey() is a function to check if the key is exists or not
			return code + "," + courses.get(code); // if the key is exists it returns code and get the title and teacher name
		}
		else{
			return "Course not found!"; // otherwise returns not found here
		}
		
	}
	
// R4
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode){
		//TODO: to be implemented
		registered.putIfAbsent(studentID, new ArrayList<>()); // putifAbsent inserts only if the key is not already present. Does not overwrite existing values.
		registered.get(studentID).add(courseCode); // Add course to student's list
		courseRegistrations.putIfAbsent(courseCode, new ArrayList<>()); // Add student to Course's list
		courseRegistrations.get(courseCode).add(studentID);

	}
	
	/**
	 * Retrieve a list of attendees.
	 * 
	 * The students appear one per row (rows end with `'\n'`) 
	 * and each row is formatted as describe in in method {@link #student}
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode){
		if (!courseRegistrations.containsKey(courseCode) || courseRegistrations.get(courseCode).isEmpty()) {
			return ""; // No attendees
		}
	
		String result = "";
		for (int studentID : courseRegistrations.get(courseCode)) {
			result += student(studentID) + "\n";
		}
		return result;
	}
	

	/**
	 * Retrieves the study plan for a student.
	 * 
	 * The study plan is reported as a string having
	 * one course per line (i.e. separated by '\n').
	 * The courses are formatted as describe in method {@link #course}
	 * 
	 * @param studentID id of the student
	 * 
	 * @return the list of courses the student is registered for
	 */
	public String studyPlan(int studentID) {
		if (!registered.containsKey(studentID) || registered.get(studentID).isEmpty()) {
			return ""; // No registered courses
		}
	
		String result = "";
		for (int courseCode : registered.get(studentID)) {
			result += course(courseCode) + "\n";  // Fetch course details
		}
		return result;
	}
	
	
	
// R5
	/**
	 * records the grade (integer 0-30) for an exam can 
	 * 
	 * @param studentId the ID of the student
	 * @param courseID	course code 
	 * @param grade		grade ( 0-30) 
	 */
	public void exam(int studentId, int courseID, int grade) {
		grades.putIfAbsent(studentId, new HashMap<>());
		grades.get(studentId).put(courseID, grade);
	}
	
	/**
	 * Computes the average grade for a student and formats it as a string
	 * using the following format 
	 * 
	 * {@code "Student STUDENT_ID : AVG_GRADE"}. 
	 * 
	 * If the student has no exam recorded the method
	 * returns {@code "Student STUDENT_ID hasn't taken any exams"}.
	 * 
	 * @param studentId the ID of the student
	 * @return the average grade formatted as a string.
	 */
	public String studentAvg(int studentId) {
		if(!grades.containsKey(studentId)){
			return "Student" + studentId + "hasn't taken any exams";
		}
		HashMap<Integer, Integer> StudentGrades = grades.get(studentId);
		int sum = 0;
		for (int grade : StudentGrades.values()){
			sum += grade;
		} 
		double avg =(double) sum / StudentGrades.size();
		
		return "Student " + studentId + " : " + avg;
	}
	
	/**
	 * Computes the average grades of all students that took the exam for a given course.
	 * 
	 * The format is the following: 
	 * {@code "The average for the course COURSE_TITLE is: COURSE_AVG"}.
	 * 
	 * If no student took the exam for that course it returns {@code "No student has taken the exam in COURSE_TITLE"}.
	 * 
	 * @param courseId	course code 
	 * @return the course average formatted as a string
	 */
	public String courseAvg(int courseId) {
		int totalGrades = 0;
		int count = 0;
		for (HashMap<Integer, Integer> studentGrades :grades.values()){
			if(studentGrades.containsKey(courseId)){ 
				totalGrades += studentGrades.get(courseId);
				count++;
			}
		}
		if (count ==  0){
			return "No student has taken the exam in" + course(courseId);
		}
		double avg = (double) totalGrades/count;
		return "The average for the course " + course(courseId) + "is:" +avg;
	}
	

// R6
	/**
	 * Retrieve information for the best students to award a price.
	 * 
	 * The students' score is evaluated as the average grade of the exams they've taken. 
	 * To take into account the number of exams taken and not only the grades, 
	 * a special bonus is assigned on top of the average grade: 
	 * the number of taken exams divided by the number of courses the student is enrolled to, multiplied by 10.
	 * The bonus is added to the exam average to compute the student score.
	 * 
	 * The method returns a string with the information about the three students with the highest score. 
	 * The students appear one per row (rows are terminated by a new-line character {@code '\n'}) 
	 * and each one of them is formatted as: {@code "STUDENT_FIRSTNAME STUDENT_LASTNAME : SCORE"}.
	 * 
	 * @return info on the best three students. 
	 */
	public String topThreeStudents() {
		HashMap<Integer, Double> studentScores = new HashMap<>();
		for (int studentId : grades.keySet()){ // Iterates over students who took the exams before
			HashMap<Integer, Integer> studentGrades = grades.get(studentId); //
			int sum  = 0;
			for(int grade : studentGrades.values()){
				sum+= grade; // Sum All grades
			}
			double avgGrade = (double) sum / studentGrades.size(); // compute the average

		int examsTaken = studentGrades.size();
		int courseRegistered = registered.get(studentId).size();

		double bonus =  (double) examsTaken / courseRegistered *10;
		double finalScore = avgGrade + bonus;

		studentScores.put(studentId, finalScore);
		}
		// The way to srt students by score (descending)

		List<Map.Entry<Integer, Double>> sortedEntries = new ArrayList<>(studentScores.entrySet());
		sortedEntries.sort((a,b) -> b.getValue().compareTo(a.getValue()));

		StringBuilder result = new StringBuilder();

		int count = 0;
		for (Map.Entry<Integer, Double> entry : sortedEntries){
			if(count >= 3) break;
			int studentId = entry.getKey();
			String fullName = students.get(studentId);
		

			result.append(String.format("%s : %.1f\n", fullName, entry.getValue()));
			count++;
		}

	return result.toString().trim();

	}

// R7
    /**
     * This field points to the logger for the class that can be used
     * throughout the methods to log the activities.
     */
    public static final Logger logger = Logger.getLogger("University");

}
