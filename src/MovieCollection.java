import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection {
    private ArrayList<Movie> movieList;
    private Scanner scanner;

    public MovieCollection(){
        scanner = new Scanner(System.in);
        movieList = new ArrayList<>();
        readData();
        mainMenu();

    }


    private void readData(){
        try {
            File myFile = new File("src\\movies_data.csv");
            Scanner fileScanner = new Scanner(myFile);
            fileScanner.nextLine();
            while (fileScanner.hasNext()) {
                String data = fileScanner.nextLine();
                String[] splitData = data.split(",");
                Movie mov = new Movie(splitData[0], splitData[1],splitData[2],splitData[3],Integer.parseInt(splitData[4]), Double.parseDouble(splitData[5]));
                movieList.add(mov);
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }


    }
    private void mainMenu(){
        System.out.println("Welcome to the movie collection!");
        String menuOption = "";

        while (!menuOption.equals("q")) {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (c)ast");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (menuOption.equals("t")) {
                searchTitles();
            } else if (menuOption.equals("c")) {
                searchCast();
            } else if (menuOption.equals("q")) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid choice!");
            }
        }

    }
    private void searchTitles(){
        System.out.print("Enter a title search term: ");
        String target = scanner.nextLine().toLowerCase();
        ArrayList<Movie> options = new ArrayList<>();
        for (Movie m:movieList){
            if (m.getName().toLowerCase().contains(target) && !options.contains(m)){
                options.add(m);
            }
        }
        if (options.size()>0){
            insertionSortMovies(options);
            int x = 1;
            for (Movie op:options) {
                System.out.println(x + ". " + op.getName());
                x++;
            }
            System.out.println();
            System.out.println("Which movie would you like to learn more about?");
            System.out.print("Enter number: ");
            int idx = scanner.nextInt()-1;
            scanner.nextLine();
            System.out.println();
            printDetails(options.get(idx));

        }else{
            System.out.println("No movie titles match that search term");
        }


    }
    private void searchCast(){
        System.out.print("Enter a person to search for (first or last name): ");
        String target = scanner.nextLine().toLowerCase();
        ArrayList<String> names = new ArrayList<>();
        for (Movie m:movieList){
            String[] mov_cast = m.getCast().split("\\|");
            for (String person:mov_cast){
                if (person.toLowerCase().contains(target)&& !names.contains(person)){
                    names.add(person);
                }
            }
        }
        if (names.size()>0) {
            insertionSortNames(names);
            int x = 1;
            for (String person : names) {
                System.out.println(x + ". " + person);
                x++;
            }
            System.out.println("Which movie would you like to see all movies for?");
            System.out.print("Enter number: ");
            int idx = scanner.nextInt()-1;
            scanner.nextLine();
            String person = names.get(idx);
            ArrayList<Movie> movsWithPerson = new ArrayList<>();

            for (Movie m : movieList){
                if (m.getCast().toLowerCase().contains(person.toLowerCase())&& !movsWithPerson.contains(m)){
                    movsWithPerson.add(m);
                }
            }
            insertionSortMovies(movsWithPerson);
            int y = 1;
            for (Movie mov:movsWithPerson) {
                System.out.println(y + ". " + mov.getName());
                y++;
            }
            System.out.println("Which movie would you like to learn more about?");
            System.out.print("Enter number: ");
            int idx2 = scanner.nextInt()-1;
            scanner.nextLine();
            System.out.println();
            printDetails(movsWithPerson.get(idx2));

        }else{
            System.out.println("No actors match that name");
        }

    }

    private void insertionSortMovies(ArrayList<Movie> movs){
        for (int i = 1; i<movs.size();i++){
            int x = i-1;
            while (x>= 0 && movs.get(i).getName().compareTo(movs.get(x).getName())<0){
                x--;
            }
            movs.add(x+1, movs.remove(i));

        }

    }
    private void insertionSortNames(ArrayList<String> names){
        for (int i = 1; i<names.size();i++){
            int x = i-1;
            while (x>= 0 && names.get(i).compareTo(names.get(x))<0){
                x--;
            }
            names.add(x+1, names.remove(i));

        }

    }

    private void printDetails(Movie movie){
        System.out.println("Title: " + movie.getName());
        System.out.println("Runtime: "+ movie.getRuntime()+" minutes");
        System.out.println("Directed by: "+ movie.getDirector());
        System.out.println("Cast: "+ movie.getCast());
        System.out.println("Overview: "+ movie.getOverview());
        System.out.println("User rating: "+ movie.getRating());

    }

}
