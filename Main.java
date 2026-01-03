import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static MovieManager<Movie> manager = new MovieManager<>();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String[] args) {
        int choice = 0;
        do {
            System.out.println("\n========== QUẢN LÝ PHIM ==========");
            System.out.println("1. Thêm phim");
            System.out.println("2. Xóa phim");
            System.out.println("3. Sửa phim");
            System.out.println("4. Hiển thị phim");
            System.out.println("5. Tìm kiếm phim theo tên");
            System.out.println("6. Lọc phim theo rating (> 8.0)");
            System.out.println("7. Thoát");
            System.out.print("Chọn chức năng: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số!");
                choice = 0;
            }

            switch (choice) {
                case 1:
                    addMovieUI();
                    break;
                case 2:
                    deleteMovieUI();
                    break;
                case 3:
                    updateMovieUI();
                    break;
                case 4:
                    manager.showAll();
                    break;
                case 5:
                    System.out.print("Nhập tên phim cần tìm: ");
                    String name = scanner.nextLine();
                    manager.searchByName(name);
                    break;
                case 6:
                    manager.filterByRating(8.0);
                    break;
                case 7:
                    System.out.println("Thoát chương trình.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (choice != 7);
    }


    private static void addMovieUI() {
        try {
            System.out.print("Nhập ID phim: ");
            String id = scanner.nextLine();
            if(manager.findMovieById(id) != null){
                System.out.println("ID này đã tồn tại!");
                return;
            }

            System.out.print("Nhập tiêu đề phim: ");
            String title = scanner.nextLine();

            System.out.print("Nhập đạo diễn: ");
            String director = scanner.nextLine();

            System.out.print("Nhập ngày phát hành (dd-MM-yyyy): ");
            String dateStr = scanner.nextLine();
            LocalDate releaseDate = LocalDate.parse(dateStr, dateFormatter);

            System.out.print("Nhập rating: ");
            double rating = Double.parseDouble(scanner.nextLine());

            Movie movie = new Movie(id, title, director, releaseDate, rating);
            manager.addMovie(movie);

        } catch (DateTimeParseException e) {
            System.out.println("Lỗi: Định dạng ngày tháng không hợp lệ (yêu cầu dd-MM-yyyy)!");
        } catch (NumberFormatException e) {
            System.out.println("Lỗi: Rating phải là một số!");
        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
    }

    private static void updateMovieUI() {
        System.out.print("Mời nhập id phim muốn sửa: ");
        String id = scanner.nextLine();

        Movie existingMovie = manager.findMovieById(id);
        if (existingMovie == null) {
            System.out.println("Không tìm thấy phim với id = " + id);
            return;
        }

        try {
            System.out.print("Nhập tiêu đề phim mới: ");
            String title = scanner.nextLine();
            if(title.isEmpty()) title = existingMovie.getTitle();

            System.out.print("Nhập đạo diễn mới: ");
            String director = scanner.nextLine();
            if(director.isEmpty()) director = existingMovie.getDirector();

            System.out.print("Nhập ngày phát hành mới (dd-MM-yyyy): ");
            String dateStr = scanner.nextLine();
            LocalDate releaseDate = existingMovie.getReleaseDate();
            if(!dateStr.isEmpty()) {
                releaseDate = LocalDate.parse(dateStr, dateFormatter);
            }

            System.out.print("Nhập rating mới: ");
            String ratingStr = scanner.nextLine();
            double rating = existingMovie.getRating();
            if(!ratingStr.isEmpty()){
                rating = Double.parseDouble(ratingStr);
            }

            Movie updatedMovie = new Movie(id, title, director, releaseDate, rating);
            manager.updateMovie(id, updatedMovie);

        } catch (DateTimeParseException e) {
            System.out.println("Lỗi: Ngày tháng sai định dạng!");
        } catch (NumberFormatException e) {
            System.out.println("Lỗi: Rating phải là số!");
        }
    }

    private static void deleteMovieUI() {
        System.out.print("Nhập ID phim cần xóa: ");
        String id = scanner.nextLine();
        manager.deleteMovie(id);
    }
}
