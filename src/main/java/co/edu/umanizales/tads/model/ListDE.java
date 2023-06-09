package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.controller.dto.ReportPetLocationGenderDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
public class ListDE {
    private NodeDE headDE;
    private int size;
    private List<Pet> pets = new ArrayList<>();


    private void addToStart(Pet data) {
    }

    public NodeDE getHead() {
        return headDE;
    }

    public List<Pet> print() {
        if (size == 0) {
            return null;
        }
        if (headDE != null) {
            NodeDE temp = headDE;
            while (temp != null) {
                pets.add(temp.getData());
                temp = temp.getNext();
            }
        }
        return pets;
    }


    public void addPet(Pet pet) throws ListDEException {
        if (pet == null) {
            throw new ListDEException("La mascota no puede ser nulo");
        }
        if (headDE == null) {
            headDE = new NodeDE(pet);
        } else {
            NodeDE newNode = new NodeDE(pet);
            NodeDE current = headDE;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
            newNode.setPrev(current);
        }
        size++;
    }

    public void addPetToStart(Pet pet) throws ListDEException {
        if (pet == null) {
            throw new ListDEException("La mascota no puede ser nulo");
        }
        if (headDE == null) {
            headDE = new NodeDE(pet);
        } else {
            NodeDE newNode = new NodeDE(pet);
            newNode.setNext(headDE);
            headDE.setPrevious(newNode);
            headDE = newNode;

        }
        size++;
    }

    public void changeExtremes() throws ListDEException {
        if (headDE != null && headDE.getNext() != null) {
            NodeDE temp = headDE;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            Pet headCopy = this.getHead().getData();
            headDE.setData(temp.getData());
            temp.setData(headCopy);
        }
    }

    public Pet getPetById(String id) {
        NodeDE temp = headDE;
        if (headDE != null) {
            while (temp != null) {
                if(temp.getData().getIdentificationPet().equals(id)) {
                    return temp.getData();
                }
                temp = temp.getNext();
            }

        }
        return null;
    }

    public void deletePetByIdentification(String identificationPet) throws ListDEException {
        if (identificationPet != null) {
            NodeDE temp = headDE;
            while (temp != null) {
                if (temp.getData().getIdentificationPet().equals(identificationPet)) {
                    NodeDE previous = temp.getPrevious();
                    NodeDE next = temp.getNext();
                    if (previous == null) {
                        headDE = next;
                    } else {
                        previous.setNext(next);
                    }
                    if (next != null) {
                        next.setPrevious(previous);
                    }
                }
                temp = temp.getNext();
            }
        } else {
            throw new ListDEException("La identificacion no puede ser nulo");
        }
    }

    public int getPosbyId(String id) {
        NodeDE temp = headDE;
        int acumulate = 0;
        if (headDE != null) {
            while (temp != null && !temp.getData().getIdentificationPet().equals(id)) {
                acumulate = acumulate + 1;
                temp.getNext();
                return acumulate;
            }
        }
        return acumulate;
    }

    public void addPetByPosition(Pet pet, int position) throws ListDEException {
        if (position < 0 || position > size) {
            throw new ListDEException("Invalid position: " + position);
        }
        NodeDE newNode = new NodeDE(pet);
        if (position == 0) {
            newNode.setNext(headDE);
            if (headDE != null) {
                headDE.setPrevious(newNode);
            }
            headDE = newNode;
        } else {
            NodeDE current = headDE;
            for (int i = 1; i < position - 1; i++) {
                current = current.getNext();
            }
            newNode.setNext(current.getNext());
            if (current.getNext() != null) {
                current.getNext().setPrevious(newNode);
            }
            current.setNext(newNode);
            newNode.setPrevious(current);
        }
    }


    //Metodos Parcial

    //Metodo 1
    //Invertir la lista
    public void invert() throws ListDEException {
        if (this.headDE == null) {
            throw new ListDEException("No hay niños para poder invertir la lista");
        } else {
            ListDE listDLECp = new ListDE();
            NodeDE temp = this.headDE;
            while (temp != null) {
                listDLECp.addPetToStart(temp.getData());
                temp = temp.getNext();
            }
            this.headDE = listDLECp.getHeadDE();
        }
    }


    //Metodo 2 
    //Machos al inicio y Hembras al final
    public void getOrderMalesToStart() throws ListDEException {
        if (headDE == null) {
            throw new ListDEException("La lista está vacía y no se puede realizar la operacion");
        }
        ListDE listCopy = new ListDE();
        NodeDE temp = headDE;
        while (temp != null) {
            if (temp.getData().getGender() == 'M') {
                listCopy.addPetToStart(temp.getData());
            } else {
                listCopy.addPet(temp.getData());
            }
            temp = temp.getNext();
        }
        headDE = listCopy.getHeadDE();
    }

    //Metodo 3
    //Intercalar macho, hembra, macho, hembra
    public void getAlternatePets() throws ListDEException {
        NodeDE males = headDE;
        NodeDE females = headDE.getNext();
        NodeDE femalesHead = females;
        if (headDE == null || headDE.getNext() == null) {
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
    public void deletePetByAge(Byte age) throws ListDEException {
        if (age == null) {
            throw new ListDEException("La edad de la mascota no puede ser nula");
        }
        NodeDE temp = headDE;
        NodeDE previo;
        while (temp != null) {
            if (temp.getData().getAge() == age) {
                previo = temp.getPrevious();
                NodeDE next = temp.getNext();
                if (previo == null) {
                    headDE = next;
                } else {
                    previo.setNext(next);
                }
                if (next != null) {
                    next.setPrevious(previo);
                }
            }
            temp = temp.getNext();
        }
    }


    //Metodo 5
    //Obtener el promedio de edad de las mascotas de la lista
    public int getLength() {
        int count = 0;
        NodeDE current = headDE;
        while (current != null) {
            count++;
            current = current.getNext();
        }
        return count;
    }

    public double getAverageAge() throws ListDEException {
        double averageAge = 0;
        NodeDE temp = this.headDE;
        if (this.headDE != null) {
            while (temp != null) {
                averageAge = averageAge + temp.getData().getAge();
                temp = temp.getNext();
            }
            averageAge = averageAge / getLength();
            return (double) averageAge;
        } else {
            throw new ListDEException("La lista está vacía");
        }
    }

    //Metodo 6
    //Generar un reporte que me diga cuantas mascotas hay de cada ciudad
    public int getCountPetByLocationCode(String code) throws ListDEException {
        if (code == null || code.isEmpty()) {
            throw new ListDEException("El código de ubicación no puede ser nulo o vacío");
        }
        int count = 0;
        if (this.headDE != null) {
            NodeDE current = this.headDE;
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
    public void winPositionPet(String identificationPet, int win) throws ListDEException {
        NodeDE temp = headDE;
        ListDE listDECp = new ListDE();
        int sum= 0;
        if (headDE != null) {
            while (temp != null) {
                if (!temp.getData().getIdentificationPet().equals(identificationPet)) {
                    listDECp.addPet(temp.getData());
                    temp = temp.getNext();
                } else {
                    temp = temp.getNext();
                }
            }
            if(win!=1) {
                sum = win - getPosbyId(identificationPet);
                listDECp.addPetByPosition(getPetById(identificationPet), sum);

            }else {
                listDECp.addToStart(getPetById(identificationPet));

            }
            this.headDE = listDECp.getHead();
        }

    }


    //Metodo 8
    //Método que me permita decirle a una mascota determinada que pierda un numero de posiciones dadas
    public void losePositionPet(String identificationPet, int lose) throws ListDEException {
        NodeDE temp = this.headDE;
        int sum= 0;
        ListDE listDECp = new ListDE();
        if(headDE != null);
        while(temp!= null){
            if(!temp.getData().getIdentificationPet().equals(identificationPet)){
                listDECp.addPet(temp.getData());
                temp= temp.getNext();
            }else {
                temp = temp.getNext();
            }

        }
        sum = lose + getPosbyId(identificationPet);
        listDECp.addPetByPosition(getPetById(identificationPet),sum);
        this.headDE = listDECp.getHead();
    }

    //Metodo 9
    //Obtener un informe de mascotas por rango de edades
    public int getReportPetByRangeAge(int firstRange, int lastRange) throws ListDEException {
        NodeDE temp = headDE;
        int count = 0;
        if (this.headDE == null) {
            throw new ListDEException("No existen mascotas para poder realizar la operación");
        } else {
            while (temp != null) {
                if (temp.getData().getAge() >= firstRange && temp.getData().getAge() <= lastRange) {
                    count++;
                }
                temp = temp.getNext();
            }
            return count;
        }
    }

    //Metodo 10
    //Implementar un método que me permita enviar al final de la lista a las mascotas que su nombre inicie con una letra dada
    public void sendToFinalPetbyLetter(char letter) throws ListDEException {
        if (this.headDE != null) {
            ListDE listCopy = new ListDE();
            NodeDE temp = this.headDE;
            char firstChar = Character.toUpperCase(letter);

            while (temp != null) {
                char firstLetter = temp.getData().getName().charAt(0);
                if (Character.toUpperCase(firstLetter) != firstChar) {
                    listCopy.addPetToStart(temp.getData());
                } else {
                    listCopy.addPet(temp.getData());
                }
                temp = temp.getNext();
            }
            this.headDE = listCopy.getHeadDE();
        } else {
            throw new ListDEException("La lista no puede estar vacia");
        }
    }


    // Método de eliminar kamicase
    // Sustentación 08/05/2023

    /*
    Creo el  metodo deletePetbyIdentification para implementar el metodo.
    Ahora creo las excepciones que en este caso serian si la cabeza ve que no tiene datos se retornaria que no hay datos,
    y la otra sería que si la posicion dada es menor a 1 o es mayor a las mascotas que hay en la lista se retornaria que
    no se puede eliminar a la mascota, ya que la posicion no es valida o no se encuetra la mascota.
    Luego empiezo a nombrar el NodeDE como temp que seria el nodo temporal, luego tendria que utilizar el getNext que seria
    el nodo que buuscaria el temp hasta encontrar el nodo a elimina.
    Acá es donde entraria la cabeza que con el uso del getNext y del getPrevious se buscaria el nodo correspodiente que se
    quiere eliminar, para establecer la cabeza ahí, y así el return haría que se devuelva el nodo eliminado

     */

    public void deletePetbyIdentification(String identificationPet) throws ListDEException {
        if (headDE == null) {
            throw new ListDEException("La lista está vacia");
        }
        NodeDE temp = headDE;
        NodeDE before, after;
        if (this.headDE != null) {
            if (this.headDE.getData().getIdentificationPet().equals(identificationPet)) {
                this.headDE = headDE.getNext();
                if (headDE != null) {
                    headDE.setPrevious(null);
                }
            } else {
                while (!temp.getData().getIdentificationPet().equals(identificationPet)) {
                    temp = temp.getNext();
                }
                temp = temp.getNext();
                if (temp.getNext() == null) {
                    before = temp.getPrevious();
                    before.setNext(null);
                } else {
                    before = temp.getPrevious();
                    after = temp.getNext();
                    before.setNext(after);
                    after.setPrevious(before);
                }
            }
        }
    }

    //Metodo de Informe de Hembras en Calor
    public void getReportOnFireByLocation(ReportPetLocationGenderDTO report){
        if (headDE != null) {
            NodeDE temp = headDE;
            while (temp != null) {
                report.updateQuantity(temp.getData().getLocation().getName(), temp.getData().getGender(),
                        temp.getData().isOnfire());
                temp = temp.getNext();
            }
        }
    }
}
