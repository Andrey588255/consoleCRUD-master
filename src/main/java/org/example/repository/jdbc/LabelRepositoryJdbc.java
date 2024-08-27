package org.example.repository.jdbc;

import org.example.model.Label;
import org.example.repository.LabelRepository;
import org.example.utils.jdbc.JdbcWorker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LabelRepositoryJdbc implements LabelRepository {

    @Override
    public Long save(Label label, Long postId) {
        Long labelId = label.getId();
        try {
             // Якщо додаєм новий запис в БД
            if (labelId == null) {
                return JdbcWorker.executeSave("INSERT INTO labels (name, post_id) VALUES(?, ?)", prepSt -> {
                    prepSt.setString(1, label.getName());
                    prepSt.setLong(2, postId);
                });
                 // Якщо оновлюємо існуючий запис
            } else {
                return JdbcWorker.executeSave("UPDATE labels SET name=? WHERE id=?", prepSt -> {
                    prepSt.setString(1, label.getName());
                    prepSt.setLong(2, labelId);
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(long id) {
         try {
            JdbcWorker.executeVoid("DELETE FROM labels WHERE id=?", id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Label get(Long id) {
        return JdbcWorker.executeGet("SELECT * FROM labels WHERE id=?;", id, this::parse);
    }

    @Override
    public List<Label> getByPostId(Long id) {
        return JdbcWorker.executeGet("SELECT * FROM labels WHERE post_id=?;", id, this::parseList);
    }

    private List<Label> parseList(ResultSet resultSet) {
        List<Label> labels = new ArrayList<>();
        try {
            while (resultSet.next()) {
                labels.add(new Label(resultSet.getLong("id"), resultSet.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return labels;
    }

    private Label parse(ResultSet set) throws SQLException {
        if (set.next()) {
            try {
                return new Label(set.getLong("id"), set.getString("name"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }
}