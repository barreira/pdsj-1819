package model;


import java.time.LocalDateTime;
import java.time.Period;
import java.time.chrono.ChronoPeriod;

public class AST {

    private Node root;


    public void add(Element e) {
        if (root == null) {
            root = new Node(e);
        } else {
            root.add(e);
        }
    }


    public Result calculate() {
        if (root != null) {
            return root.calculate();
        }

        return null;
    }


    private class Node {

        private Element element;
        private Node left;
        private Node right;

        Node(Element element) {
            this.element = element;
        }


        Node(Node n) {
            this.element = n.element;
            if (n.left != null) {
                this.left = new Node(n.left);
            }

            if (n.right != null) {
                this.right = new Node(n.right);
            }
        }


        void add(Element e) {
            if (e.getPriority() < element.getPriority()) {
                if (left == null) {
                    left = new Node(element);
                    element = e;
                } else {
                    Node subtree = new Node(this);
                    this.element = e;
                    this.left = subtree;
                }

                this.right = null;
            } else {
                if (right != null) {
                    right.add(e);
                } else {
                    right = new Node(e);
                }
            }
        }


        Result calculate() {
//            System.out.print("( ");
//            if (element instanceof SubtractionElement) {
//                System.out.print(" - ");
//            } else if (element instanceof AdditionElement) {
//                System.out.print(" + ");
//            } else if (element instanceof IntervalElement) {
//                System.out.print(" , ");
//            } else if (element instanceof PeriodElement) {
//                System.out.print(" " + ((PeriodElement)element).get().toString() + " ");
//            } else if (element instanceof LocalDateTimeElement) {
//                System.out.print(" " + ((LocalDateTimeElement)element).get().toString() + " ");
//            }
            if (left == null || right == null) {
                if (element instanceof PeriodElement) {
                    return new PeriodResult(((PeriodElement)(element)).get());
                } else {
                    return new LocalDateTimeResult(((LocalDateTimeElement)(element)).get());
                }
            }

            Result resLeft = left.calculate();
            Result resRight = right.calculate();

            if (element instanceof AdditionElement) {
                if (resLeft instanceof PeriodResult) {
                    Period periodLeft = ((PeriodResult)resLeft).get();
                    if (resRight instanceof PeriodResult) {
                        Period periodRight = ((PeriodResult)resRight).get();
                        return new PeriodResult(periodLeft.plus(periodRight));
                    } else {
                        LocalDateTime localDateTime = ((LocalDateTimeResult)resRight).get();
                        return new LocalDateTimeResult(localDateTime.plus(periodLeft));
                    }
                } else {
                    LocalDateTime localDateTimeLeft = ((LocalDateTimeResult)resLeft).get();

                    if (resRight instanceof PeriodResult) {
                        Period periodRight = ((PeriodResult)resRight).get();
                        return new LocalDateTimeResult(localDateTimeLeft.plus(periodRight));
                    }
                }
            } else if (element instanceof SubtractionElement) {
                if (resLeft instanceof PeriodResult) {
                    Period periodLeft = ((PeriodResult)resLeft).get();
                    if (resRight instanceof PeriodResult) {
                        Period periodRight = ((PeriodResult)resRight).get();
                        return new PeriodResult(periodLeft.minus(periodRight));
                    } else {
                        LocalDateTime localDateTime = ((LocalDateTimeResult)resRight).get();
                        return new LocalDateTimeResult(localDateTime.minus(periodLeft));
                    }
                } else {
                    LocalDateTime localDateTimeLeft = ((LocalDateTimeResult)resLeft).get();

                    if (resRight instanceof PeriodResult) {
                        Period periodRight = ((PeriodResult)resRight).get();
                        return new LocalDateTimeResult(localDateTimeLeft.minus(periodRight));
                    }
                }
            } else {
                LocalDateTime localDateTimeLeft = ((LocalDateTimeResult)resLeft).get();
                LocalDateTime localDateTimeRight = ((LocalDateTimeResult)resRight).get();

                return new PeriodResult(Period.between(localDateTimeLeft.toLocalDate(),
                        localDateTimeRight.toLocalDate()));
            }

            return null;
        }
    }
}
