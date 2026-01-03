import java.util.ArrayList;
import java.util.List;

public class MovieManager<T extends Movie> {
    private List<T> movieList;

    public MovieManager() {
        this.movieList = new ArrayList<>();
    }

    public void addMovie(T movie) {
        movieList.add(movie);
        System.out.println("Phim đã được thêm thành công.");
    }

    public void showAll() {
        System.out.println("\nDanh sách phim:");
        if (movieList.isEmpty()) {
            System.out.println("(Danh sách trống)");
        } else {
            for (T movie : movieList) {
                System.out.println(movie);
            }
        }
    }

    public boolean deleteMovie(String id) {
        T movie = findMovieById(id);
        if (movie != null) {
            movieList.remove(movie);
            System.out.println("Phim đã được xóa thành công.");
            return true;
        }
        System.out.println("Không tìm thấy phim muốn xóa !");
        return false;
    }

    public T findMovieById(String id) {
        for (T movie : movieList) {
            if (movie.getId().equalsIgnoreCase(id)) {
                return movie;
            }
        }
        return null;
    }

    public void searchByName(String name) {
        boolean found = false;
        System.out.println("Kết quả tìm kiếm:");
        for (T movie : movieList) {
            if (movie.getTitle().toLowerCase().contains(name.toLowerCase())) {
                System.out.println(movie);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Không tìm thấy phim nào tên: " + name);
        }
    }

    public void filterByRating(double minRating) {
        System.out.println("Các phim có rating > " + minRating + ":");
        boolean found = false;
        for (T movie : movieList) {
            if (movie.getRating() > minRating) {
                System.out.println(movie);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Không có phim nào thỏa mãn.");
        }
    }

    public void updateMovie(String id, T newMovieData) {
        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getId().equalsIgnoreCase(id)) {
                movieList.set(i, newMovieData);
                System.out.println("Cập nhật phim thành công !");
                return;
            }
        }
        System.out.println("Không tìm thấy phim với id = " + id);
    }
}
