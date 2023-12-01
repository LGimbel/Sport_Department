import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
public class Main {
    static class School {
        //region School specific Declarations
        static  Scanner input = new Scanner(System.in);
        static  final String ListOfSports = "1: Jousting\n2: Unicycle Hockey\n3: Timber Sports\n4:Ostrich Racing\n 5: Air Hockey";
        static  final String[] SportsList = new String[]{"Jousting", "Unicycle Hockey", "Timber Sports" ,"Ostrich Racing", "Air Hockey"};
        static  Map<String,List<String>> Roster = new HashMap<>();
        static int repetition = 0;
        static String  studentName = "";
        //endregion
        static void BaseUI() {
            System.out.println("Are you a student? Y/N");
            String queryStudentStatus = input.nextLine();
            if (queryStudentStatus.isEmpty()) {
                System.out.println("Answer can't be empty please try again.");
                BaseUI();
            } else {
                switch (queryStudentStatus) {
                    case "y", "Y" -> Student.StudentUIIntro(input, Roster, ListOfSports, SportsList);
                    case "n", "N" -> {
                        System.out.println("Are you a Department Manager? Y/N");
                        String queryManagerStatus = input.nextLine();
                        if (queryManagerStatus.isEmpty()) {
                            System.out.println("Answer can't be empty please try again.");
                            BaseUI();
                        } else {
                            switch (queryManagerStatus) {
                                case "y", "Y" -> {
                                }
                                // call department manager UI
                                default -> {
                                    System.out.println("Authentication error. Please try again.");
                                    BaseUI();
                                }
                            }
                        }
                    }
                    default -> {
                        System.out.println("Authentication error. Please try again.");
                        BaseUI();
                    }
                }

            }
        }
        static class Student extends School {
            static void StudentUIIntro(Scanner input, Map<String, List<String>> Roster, String ListOfSports, String[] SportsList) {
                if (repetition == 0) {
                   studentName = GetStudentName(input);
                }
                 repetition++;

                final String MENUOPTIONS = "1: Enroll in a new sport. \n2: Switch to a different sport. \n3: Drop a currently enrolled sport. \n4: Switch user type. \n5:Quit program.";
                System.out.println("Welcome to student sports registration");

                System.out.println("Please select the option that you wish to take by using inputting the corresponding number");
                System.out.println(MENUOPTIONS);
                int SelectionChoice = input.nextInt();
                StudentInterfaceLogic(SelectionChoice, Roster, input, studentName, ListOfSports, SportsList);

            }

            static void StudentInterfaceLogic(int choice, Map<String, List<String>> Roster, Scanner input, String studentName, String ListOfSports, String[] SportsList) {
                switch (choice) {
                    case 1 -> {
                        EnrollSport(input, Roster, ListOfSports, studentName, SportsList);
                    }


                    case 2 -> {
                        SwitchSport(input, Roster, ListOfSports, studentName, SportsList);
                    }

                    case 3 -> {

                    }
                    case 4 -> School.BaseUI();
                    case 5 -> {
                        System.out.println("Are you sure you want to quit? Y/N");
                        String quit = input.nextLine();
                        if (quit.equals("Y") || (quit.equals("y"))) {
                            System.out.println("Thank you for using the system have a wonderful day!");
                            System.exit(143);
                        } else {
                            System.out.println("Redirecting back to student menu.");
                            StudentUIIntro(input, Roster, ListOfSports, SportsList);
                        }
                    }
                    default -> {
                        System.out.println("Please try again");
                        System.out.println("Redirecting back to student menu.");
                        StudentUIIntro(input, Roster, ListOfSports, SportsList);
                    }
                }
            }

            static String GetStudentName(Scanner input) {
                System.out.println("Please input your name.");
                String studentName = input.nextLine();
                if (studentName.isEmpty()) {
                    System.out.println("Name can't be empty please try again.");
                    GetStudentName(input);
                } else {
                    return studentName;
                }
                return "\0";
            }

            static void EnrollSport(Scanner input, Map<String, List<String>> Roster, String ListOfSports, String studentName, String[] SportsList) {
                if (Roster.containsKey(studentName)) {
                    List<String> sportsPlayed = Roster.get(studentName);
                    if (sportsPlayed != null && sportsPlayed.size() > 3) {
                        System.out.println("Unfortunately it appears you are already playing the most amount of sports allowed if you want to join a new one you will first have to drop a different sport.");
                        StudentUIIntro(input, Roster, ListOfSports, SportsList);
                    }
                } else {

                    System.out.println("please enter either the name or corresponding number or the sport you would like to enroll in");
                    System.out.println(ListOfSports);
                    if (input.hasNextInt()) {
                        addSport(input.nextInt(), Roster, SportsList, studentName);
                        StudentUIIntro(input, Roster, ListOfSports, SportsList);
                    } else {
                        addSport(input.nextLine(), Roster, SportsList, studentName);
                        StudentUIIntro(input, Roster, ListOfSports, SportsList);

                    }
                }

            }

            static void SwitchSport(Scanner input, Map<String, List<String>> Roster, String ListOfSports, String studentName, String[] SportsList) {
                List<String> TempList = Roster.get(studentName);
                System.out.println("Please Chose What sport to drop.\nThe sports you are currently taking are " + TempList);
                String SportToDrop = input.nextLine();
                if (TempList.contains(SportToDrop)) {
                    Roster.remove(studentName);
                    TempList.remove(SportToDrop);
                    Roster.put(studentName, TempList);
                    System.out.println("Sport dropped");
                } else {
                    System.out.println("It appears that you do not play the selected sport please try again");
                    DropSport(input, Roster, ListOfSports, studentName, SportsList);
                }
                EnrollSport(input, Roster, ListOfSports, studentName, SportsList);
            }

            static void DropSport(Scanner input, Map<String, List<String>> Roster, String ListOfSports, String studentName, String[] SportsList) {
                List<String> TempList = Roster.get(studentName);
                System.out.println("Please Chose What sport to drop.\nThe sports you are currently taking are " + TempList);
                String SportToDrop = input.nextLine();
                if (TempList.contains(SportToDrop)) {
                    Roster.remove(studentName);
                    TempList.remove(SportToDrop);
                    Roster.put(studentName, TempList);
                    System.out.println("Sport dropped");
                    StudentUIIntro(input, Roster, ListOfSports, SportsList);

                } else {
                    System.out.println("It appears that you do not play the selected sport please try again");
                    DropSport(input, Roster, ListOfSports, studentName, SportsList);
                }
            }

            //region add sport stuff also Polymorphism!!
            static void addSport(int choice, Map<String, List<String>> Roster, String[] SportsList, String studentName) {
                System.out.println("You chose to enroll in: " + SportsList[(choice - 1)]);
                addToRoster(Roster, studentName, SportsList[choice - 1]);
                System.out.println("Sport Added");

            }

            static void addSport(String choice, Map<String, List<String>> Roster, String[] SportsList, String studentName) {
                System.out.println("You chose to enroll in: " + choice);
                addToRoster(Roster, studentName, choice);
                System.out.println("Sport Added");
            }

            private static void addToRoster(Map<String, List<String>> Roster, String name, String sport) {
                Roster.computeIfAbsent(name, key -> new ArrayList<>()).add(sport);


            }
            //endregion

            static class DepartmentManager extends School {
                static void DepartmentManagerUI(Map<String, List<String>> Roster){
                    System.out.println("Please select the desired action by typing the corresponding number");
                    System.out.println("1: View all sports player by a specific student\n2: View the entire roster\n3: Switch user type\n4: exit the program");
                    int choice = input.nextInt();

                    switch (choice) {

                        case 1 -> {
                            System.out.println("Please enter the name of the student.");
                            String student = input.nextLine();
                            GetStudentSports(student,Roster);

                        }
                        case 2 -> {
                            ViewRoster(Roster);
                        }
                        case 3 -> School.BaseUI();
                        case 4 -> {
                            System.out.println("Are you sure you want to quit? Y/N");
                            String quit = input.nextLine();
                            if (quit.equals("Y") || (quit.equals("y"))) {
                                System.out.println("Thank you for using the system have a wonderful day!");
                                System.exit(143);
                            } else {
                                System.out.println("Redirecting back to student menu.");
                                StudentUIIntro(input, Roster, ListOfSports, SportsList);
                            }

                        }
                    }
                }

                static void ViewRoster(Map<String, List<String>> Roster){
                    System.out.println(Roster);
                    DepartmentManagerUI(Roster);
                }
                static void GetStudentSports(String name,Map<String, List<String>> Roster){
                    if (Roster.containsKey(name)) {
                        System.out.println(Roster.get(name));
                        DepartmentManagerUI(Roster);
                    }
                    else {
                        System.out.println("It appears that the student you entered is not on the roster please try again.");
                        DepartmentManagerUI(Roster);
                    }
                }
            }
        }
    public static void main(String[] args) {
        School.BaseUI();
}
}
}