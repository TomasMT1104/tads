package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.exception.ListDEException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class ListDE {
    private NodeDE headDE;
    private int size;
    private NodeDE head;
    private Object tail;

    private void addToStart(Pet data) {
    }

    public NodeDE getHead() {
        return headDE;
    }

    public void add(Pet pet) {
        if (headDE != null) {
            NodeDE temp = headDE;
            while (temp.getNext() != null) {
                temp.getNext();
            }
            NodeDE newNodeDE = new NodeDE(pet);
            temp.setNext(newNodeDE);
            newNodeDE.setNext(newNodeDE);
        } else {
            headDE = new NodeDE(pet);
        }

    }


    //Metodos Parcial

    //Metodo 1
    //Invertir la lista
    public void invert() throws NullPointerException {
        if (this.head != null) {
            ListDE listCp = new ListDE();
            NodeDE temp = this.head;
            while (temp != null) {
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
            NodeDE currentNode = this.head;
            while (currentNode.getNext() != null) {
                NodeDE nextNode = currentNode.getNext();
                nextNode.setPrev(currentNode);
                currentNode = nextNode;
            }
        } else {
            throw new NullPointerException("Cabeza es nulo");
        }
    }
    
    //Metodo 2 
    //Machos al inicio y Hembras al final
    public void getorderBoysToStart() throws ListDEException {
        if (this.head != null) {
            ListDE listDoublyLinked = new ListDE();
            NodeDE temp = this.head;
            NodeDE lastBoy = null;
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    if (lastBoy != null) {
                        listDoublyLinked.addToStart(lastBoy.getData());
                    }
                    lastBoy = temp;
                } else {
                    listDoublyLinked.add(temp.getData());
                }
                temp = temp.getNext();
            }
            if (lastBoy != null) {
                listDoublyLinked.addToStart(lastBoy.getData());
            }
            this.head = listDoublyLinked.getHead();
            this.tail = listDoublyLinked.getTail();
        } else {
            throw new ListDEException ("La lista está vacía");
        }
    }

    //Metodo 3
    //Intercalar macho, hembra, macho, hembra
    public void getAlternatePets() throws ListDEException {
        NodeDE males = head;
        NodeDE females = head.getNext();
        NodeDE femalesHead = females;
        if (head == null || head.getNext() == null) {
            throw new ListDEException("La lista esta vacia o solo tiene un elemento");
        }
        while (females != null && males != null) {
            males.setNext(females.getNext());
            if (females.getNext() != null) {
                females.getNext().setPrevious(females.getPrevious());
            }
            females.setPrevious(males);
            males = males.getNext();
            if (males != null) {
                females = males.getNext();
            }
        }
        if (females == null) {
            males.setNext(femalesHead);
            if (femalesHead != null) {
                femalesHead.setPrev(males);
            }
        } else {
            females.setPrevious(males);
            if (males != null) {
                males.setNext(females);
            }
        }
    }

    //Metodo 4
    //Dada una edad eliminar de la lista a las mascotas de la edad dada
    public void deletePetbyAge(Node head, byte age) throws ListDEException {
        if (age <= 0) {
            throw new ListDEException("La edad debe ser un valor positivo mayor que cero");
        }
        Node temp = head;
        ListDE listDLECp = new ListDE();
        while (temp != null) {
            if (temp.getData().getAge() != age) {
                listDLECp.addToStart(temp.getData());
            }
            temp = temp.getNext();
        }
        if (listDLECp.getHead() == null) {
            throw new ListDEException("No hay mascotas con la edad dada en la lista");
        }
        this.head = listDLECp.getHead();
        this.head.setPrev(null);
        NodeDE tail = this.head;
        while (tail.getNext() != null) {
            tail = tail.getNext();
            tail.setPrev(tail.getPrevious().getPrevious());
        }
    }
    
    //Metodo 5
    //Obtener el promedio de edad de las mascotas de la lista
    public int getLength() {
        int count = 0;
        NodeDE current = head;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    public double getAverageAge() throws ListDEException {
        double averageAge = 0;
        NodeDE temp = this.head;
        if (this.head != null) {
            while (temp != null) {
                averageAge = averageAge + temp.getData().getAge();
                temp = temp.getNext();
            }
            averageAge = averageAge / getLength();
            return averageAge;
        } else {
            throw new ListDEException("La lista está vacía");
        }
    }

    public void deletePetByAge(byte age) throws ListDEException {
        if (age <= 0) {
            throw new ListDEException("La edad debe ser un valor positivo mayor que cero");
        }
        NodeDE temp = this.head;
        while (temp != null) {
            if (temp.getData().getAge() == age) {
                if (temp == this.head) {
                    this.head = temp.getNext();
                    if (this.head != null) {
                        this.head.setPrevious(null);
                    }
                } else if (temp == this.tail) {
                    this.tail = temp.getPrevious();
                    if (this.tail != null) {
                        this.tail.setNext(null);
                    }
                } else {
                    temp.getPrev().setNext(temp.getNext());
                    temp.getNext().setPrevious(temp.getPrevious());
                }
            }
            temp = temp.getNext();
        }
    }

    //Metodo 6
    //Generar un reporte que me diga cuantas mascotas hay de cada ciudad
    public int getCountPetByLocationCode(String code) throws ListDEException {
        if (code == null || code.isEmpty()) {
            throw new ListDEException("El código de ubicación no puede ser nulo o vacío");
        }
        int count = 0;
        if (this.head != null) {
            NodeDE current = this.head;
            while (current != null) {
                if (current.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                NodeDE previous = current;
                current = current.getNext();
                if (current != null) {
                    current.setPrevious(previous);
                }
            }
        } else {
            return 0;
        }
        return count;
    }

    //Metodo 7
    //Método que me permita defirirle a una mascota determinado que adelante un número dado de posiciones
    public void MovePet(String identification, int posicion) {
        NodeDE current = head;
        NodeDE targetNode = null;
        int currentPos = 0;
        while (current != null) {
            if (current.getData().getIdentification().equals(identification)) {
                targetNode = current;
                break;
            }
            current = current.getNext();
        }
        if (targetNode.getData() == null) {
            System.out.println("La mascota con esta identificación no está en la lista");
            return;
        }
        current = head;
        while (current != null && currentPos < posicion) {
            current = current.getNext();
            currentPos++;
        }
        if (currentPos < posicion) {
            System.out.println("La posición a la que desea moverse está más allá del final de la lista");
            return;
        }
        NodeDE prevNode = current.getPrevious();
        targetNode.setNext(current);
        targetNode.setPrev(prevNode);
        current.setPrev(targetNode);
        if (prevNode != null) {
            prevNode.setNext(targetNode);
        } else {
            head = targetNode;
        }
    }

    //Metodo 8
    //Método que me permita decirle a una mascota determinada que pierda un numero de posiciones dadas
    public int getPostByIdReverse(String id) throws ListDEException {
        if (head == null) {
            throw new ListDEException("La lista está vacía");
        }
        int count = getLength() - 1;
        NodeDE temp = (NodeDE) tail;
        while (temp != null) {
            if (temp.getData().getIdentification().equals(id)) {
                return count;
            }
            temp = temp.getPrev();
            count--;
        }
        return -1;
    }

    //Metodo 9
    //Obtener un informe de mascotas por rango de edades
    public void reportByAge(byte minAge, byte maxAge) throws ListDEException {
        if (head == null) {
            throw new ListDEException("La lista está vacía.");
        }
        NodeDE current = head;
        boolean found = false;
        while (current != null) {
            byte edad = current.getData().getAge();
            if (edad >= minAge && edad <= maxAge) {
                String name = current.getData().getName();

                found = true;
            }
            current = current.getNext();
        }
        if (!found) {
            throw new ListDEException("No se encontraron mascotas dentro del rango de edad especificado.");
        }
    }

    //Metodo 10
    //Implementar un método que me permita enviar al final de la lista a las mascotas que su nombre inicie con una letra dada
    public void movePet(char letter) throws ListDEException {
        if (head == null) {
            throw new ListDEException("La lista está vacía");
        }
        NodeDE current = head;
        NodeDE last = null;
        while (current != null) {
            if (current.getData().getName().startsWith(String.valueOf(letter))) {
                if (last == null) {
                    head = current.getNext();
                } else {
                    last.setNext(current.getNext());
                }
                if (current.getNext() != null) {
                    current.getNext().setPrev(last);
                }
                if (tail == null) {
                    tail = current;
                } else {
                    tail.setNext(current);
                    current.setPrev((NodeDE) tail);
                    tail = current;
                }
                current = current.getNext();
                tail.setNext(null);
                tail.setPrevious(last);
            } else {
                last = current;
                current = current.getNext();
            }
        }
    }

















}
