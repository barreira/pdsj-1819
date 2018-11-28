/**
 * Classe auxiliar para a exibição de resultados por página.
 *
 * @author Diogo Silva  - A76407
 * @author Rafael Braga - A61799
 * @version 2018.11.25
 */

package controller;

import java.util.List;
import java.util.ArrayList;

public class Paging {
    private final List<String> elements; // list of elements to display
    private int currentPage; // index of current page
    private final int pageSize; // number of elements per page
    private final int totalPages; // total number of pages

    Paging(List<String> elements, int pageSize) {
        this.elements = new ArrayList<>(elements);
        this.currentPage = 0;
        this.pageSize = pageSize;
        this.totalPages = (int) Math.ceil((double) elements.size() / pageSize);
    }

    Paging(Paging p) {
        this.pageSize = p.getPageSize();
        this.currentPage = 0;
        this.totalPages = p.getTotalPages();
        this.elements = p.getElements();
    }

    List<String> getElements() {
        return elements;
    }

    int getPageSize() {
        return pageSize;
    }

    int getTotalPages() {
        return totalPages;
    }

    int getCurrentPage() {
        return currentPage + 1;
    }

    String getElement(int indexOfPage) {
        try {
            return elements.get(currentPage * pageSize + indexOfPage);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    List<String> currentPage() {
        List<String> page = new ArrayList<>();
        for (int i = currentPage; i < currentPage + pageSize && i < elements.size() ; i++) {
            page.add(elements.get(i));
        }
        return page;
    }

    List<String> nextPage() {
        final List<String> page = new ArrayList<>();
        int i;

        if(currentPage + 1 == totalPages) {
            for (i = currentPage * pageSize; i < elements.size(); i++) {
                page.add(elements.get(i));
            }
        } else  {
            currentPage++;
            for (i = currentPage * pageSize; i < currentPage * pageSize + pageSize && i < elements.size(); i++) {
                page.add(elements.get(i));
            }
        }
        return page;
    }

    List<String> previousPage() {
        List<String> page = new ArrayList<>();

        if (currentPage == 0) {
            for (int i = 0; i < pageSize && i < elements.size(); i++) {
                page.add(elements.get(i));
            }
        } else if (currentPage + 1 == totalPages) {
            int n = pageSize % elements.size();
            for (int i = currentPage * pageSize - n; i < currentPage * pageSize; i++) {
                page.add(elements.get(i));
            }
            currentPage--;
        } else {
            for (int i = currentPage * pageSize - pageSize; i < currentPage * pageSize; i++) {
                page.add(elements.get(i));
            }
            currentPage--;
        }

        return page;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        
        Paging p = (Paging) o;
        return totalPages == p.getTotalPages() && pageSize == p.getPageSize() && elements.equals(p.getElements());
    }

    public String toString() {
        return "(ps = " + pageSize + ", tp = " + totalPages + ", cp = " + currentPage + "e =\n" +
                String.join("\n", elements) + ")";
    }

    public Paging clone() {
        return new Paging(this);
    }
}
