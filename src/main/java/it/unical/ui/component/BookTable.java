package it.unical.ui.component;

import it.unical.model.Book;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.BiConsumer;

public class BookTable extends JTable {
    public BookTable(List<Book> books, BiConsumer<Integer, Book> onBookClick) {
        super(new BookTableModel(books));

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(getModel());
        setRowSorter(sorter);

        setRowHeight(30);
        setFillsViewportHeight(true);
        setShowGrid(false);
        setIntercellSpacing(new Dimension(0, 0));

        JTableHeader header = getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(60, 63, 65));
        header.setReorderingAllowed(false);

        setFont(new Font("SansSerif", Font.PLAIN, 13));
        setForeground(Color.DARK_GRAY);

        setDefaultRenderer(Object.class, new BookTableCellRenderer());

        getColumnModel().getColumn(0).setPreferredWidth(120); // ISBN
        getColumnModel().getColumn(1).setPreferredWidth(250); // Title
        getColumnModel().getColumn(2).setPreferredWidth(180); // Author
        getColumnModel().getColumn(3).setPreferredWidth(60);  // Rating

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = rowAtPoint(e.getPoint());

                if (row >= 0) {
                    int modelRow = convertRowIndexToModel(row);
                    Book selectedBook = books.get(modelRow);
                    onBookClick.accept(row, selectedBook);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
    }

    private static class BookTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column
        ) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(SwingConstants.CENTER);
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            component.setBackground(row % 2 == 0 ? new Color(245, 245, 245) : Color.WHITE);
            component.setForeground(Color.DARK_GRAY);

            if (isSelected) {
                component.setBackground(new Color(180, 205, 255));
                component.setForeground(Color.BLACK);
            }

            if (column != 3) return component;

            Integer rating = (Integer) value;
            StringBuilder text = new StringBuilder();
            for (Integer i = 1; i <= 5; i++) {
                text.append(rating - i >= 0 ? "★" : "☆");
            }
            setText(text.toString());

            return component;
        }
    }

    private static class BookTableModel extends AbstractTableModel {
        private final List<Book> books;
        private final String[] columnNames = {"ISBN", "Title", "Author", "Rating"};

        public BookTableModel(List<Book> books) {
            this.books = books;
        }

        @Override
        public int getRowCount() {
            return books.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Book book = books.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> book.getIsbn();
                case 1 -> book.getTitle();
                case 2 -> book.getAuthor();
                case 3 -> book.getRating();
                default -> null;
            };
        }

        @Override
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }
    }
}
