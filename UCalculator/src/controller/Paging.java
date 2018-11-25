/**
 * Classe auxiliar para a exibição de resultados por página.
 * 
 * @author Diogo Silva    - A76407
 * @author Rafael Braga   - A61799
 * @version 2018.11.25
 */

package controller;

import static java.util.stream.Collectors.joining;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

public class Paging
{
    // Variáveis de instância
    
    private int pageSize;   // Número de elementos a exibir por cada página.
    private int totalPages;      // Número total de páginas.
    private int currentPage;          // Página atual.
    private int index;        // Indice atual da lista de strings.
    private List<String> elements; // Lista de strings dos elementos a exibir.
    
    
    // Construtores
    
    /**
     * Construtor por partes.
     * 
     * @param pageSize Número de elementos a exibir por cada página.
     * @param lst        Lista de strings dos elementos a exibir.
     */    
    public Paging(int pageSize, List<String> lst) {
        this.pageSize = pageSize;
        this.elements = new ArrayList<>(lst);
        index = 0;
        currentPage = 0;
        totalPages = calcNumPags();
    }
    
    /**
     * Construtor de cópia.
     * 
     * @param p Uma instância da classe Paginacao.
     */
    Paging(Paging p) {
        pageSize = p.getPageSize();
        totalPages = p.getTotalPages();
        index = p.getIndex();
        elements = p.getElements();
    }
    
    
    // Métodos de instância
    
    /**
     * @return Devolve campo "pageSize" de uma instância da classe Paginacao.
     */    
    int getPageSize() {
        return pageSize;
    }
    
    /**
     * @return Devolve campo "totalPages" de uma instância da classe Paginacao.
     */    
    int getTotalPages() {
        return totalPages;
    }
    
    /**
     * @return Devolve campo "currentPage" de uma instância da classe Paginacao.
     */    
    int getCurrentPage() {
        return currentPage;
    }
    
    /**
     * @return Devolve campo "index" de uma instância da classe Paginacao.
     */     
    int getIndex() {
        return index;
    }
    
    /**
     * @return Devolve campo "elements" de uma instância da classe Paginacao.
     */     
    List<String> getElements() {
        return elements;
    }
    
    /**
     * @return Devolve o número de elementos presentes no campo "elements" de uma instância da classe Paginacao.
     */     
    int getNumElements() {
        return elements.size();
    }

    List<String> nextPage() {
        final List<String> ret = new ArrayList<>();
        int i;

        if(currentPage + 1 == totalPages) {
            for (i = index; i < elements.size(); i++) {
                ret.add(elements.get(i));
            }
            currentPage++;
        } else if (currentPage < totalPages - 1){
            for (i = 0; i < pageSize && index < elements.size(); i++, index++) {
                ret.add(elements.get(index));
            }
            currentPage++;
        } else {
            for (i = index; i < elements.size(); i++) {
                ret.add(elements.get(i));
            }
        }
        return ret;
    }

    List<String> previousPage() {
        List<String> list = new ArrayList<>();

        if (currentPage > 1 && currentPage < totalPages) {
            index -= (2 * pageSize);
            currentPage--;

            for (int i = 0; i < pageSize && index < elements.size(); i++, index++) {
                list.add(this.elements.get(index));
            }
        } else if (currentPage == totalPages){
            index -= pageSize;
            currentPage--;
            for (int i = 0; i < pageSize && index < elements.size(); i++, index++) {
                list.add(this.elements.get(index));
            }
        } else {
            for (int i = 0; i < pageSize; i++) {
                list.add(this.elements.get(i));
            }
        }

        return list;
    }
    
    // Métodos complementares comuns
    
    /**
     * Verifica se um objeto é igual a uma instância da classe Paginacao.
     * 
     * @param o Objeto ao qual se quer efetuar a comparação.
     * @return Devolve true caso o objeto seja igual ou false, caso contrário.
     */    
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null || (this.getClass() != o.getClass())) {
            return false;
        }
        
        Paging p = (Paging) o;
        
        return (pageSize == p.getPageSize() && totalPages == p.getTotalPages() && currentPage == p.getCurrentPage() &&
                index == p.getIndex() && elements.equals(p.getElements()));
    }
    
    
    /**
     * @return Devolve um código de hash relativo a uma instância da classe Paginacao.
     */ 
    public int hashCode() {
        return Arrays.hashCode(new Object[] {pageSize, totalPages, currentPage, index, elements});
    }
    
    
    /**
     * @return Devolve uma representação textual de uma instância da classe Paginacao.
     */ 
    public String toString() {
        StringBuilder sb = new StringBuilder("Paginacao: ");
        
        sb.append(pageSize);
        sb.append(" ");
        sb.append(totalPages);
        sb.append(" ");
        sb.append(currentPage);
        sb.append(" ");
        sb.append(index);
        sb.append("\n");
        
        sb.append(elements.stream()
                     .collect(joining("\n")));
        
        return sb.toString();
    }
    

    /**
     * @return Devolve uma cópia de um imóvel.
     */
    public Paging clone() {
        return new Paging(this);
    }


    // Métodos privados
    
    /**
     * Calcula o número de paginas necessárias para exibir as strings da lista dos elementos, colocando o
     * resultado no campo "totalPages".
     */
    private int calcNumPags() {
        double aux = elements.size();
      
        /* Divide o tamanho da lista de strings pelo número de elementos a exibir por página */

        return  (int) Math.ceil(aux / pageSize);
    }

    String get(int op) {
        try {
            return currentPage == 0 ? elements.get(op) : elements.get((currentPage - 1) * pageSize + op);
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }
}