package structure;

import java.util.ArrayList;

import model.Book;

public class BookBST {
    private BookNode root;

    public void insert(Book book){
    root = insert(root, book);
  }

    private BookNode insert(BookNode node, Book book){
        if(node == null){
           
            return new BookNode(book);
        }
        int cmp = book.getIsbn().compareTo(node.getBook().getIsbn());
        if (cmp < 0){
            node.setLeft(insert(node.getLeft(),book));
        }
         else if (cmp > 0){
            node.setRight((insert(node.getRight(),book)));
        }
        else{
            return node;
        }
        updateHeight(node);
        int balance = getBalance(node);
        if (balance > 1 && book.getIsbn().compareTo(node.getLeft().getBook().getIsbn()) < 0){
       return rightRotate(node);
   }
       if (balance > 1 && book.getIsbn().compareTo(node.getLeft().getBook().getIsbn()) > 0){

      node.setLeft(leftRotate(node.getLeft()));
      return rightRotate(node);
    }
    if (balance < -1 &&
      book.getIsbn().compareTo(node.getRight().getBook().getIsbn()) > 0){
      return leftRotate(node);
}
     if (balance < -1 &&book.getIsbn().compareTo(node.getRight().getBook().getIsbn()) < 0){
    node.setRight(rightRotate(node.getRight()));
    return leftRotate(node);
}

  return node;
}

public Book search(String isbn){
   BookNode node = search(root,isbn);
   if (node == null){
       return null;
   }
   else{
       return node.getBook();
   }
}
private BookNode search(BookNode node, String isbn){
   if (node == null){
       return node;
   }
    int cmp =isbn.compareTo(node.getBook().getIsbn());
    if (cmp<0){
     return search(node.getLeft(),isbn);
    }
    else if(cmp>0){
      return search(node.getRight(),isbn);
    }
    else{
       return node;
    }
} 

private BookNode findMin(BookNode node){
   while(node.getLeft() != null){
     node=node.getLeft();
   }
 return node;
}

public void delete(String isbn) {
    root = delete(root, isbn);
}

private BookNode delete(BookNode node, String isbn) {

    if (node == null)
        return null;

    int cmp = isbn.compareTo(node.getBook().getIsbn());

    // ابحث عن العقدة
    if (cmp < 0) {
        node.setLeft(delete(node.getLeft(), isbn));
    }
    else if (cmp > 0) {
        node.setRight(delete(node.getRight(), isbn));
    }

    // وجدنا العقدة
    else {

        // لا يوجد أبناء أو يوجد ابن واحد
        if (node.getLeft() == null || node.getRight() == null) {

            BookNode temp;

            if (node.getLeft() != null)
                temp = node.getLeft();
            else
                temp = node.getRight();

            // لا يوجد أبناء
            if (temp == null) {
                return null;
            }

            // يوجد ابن واحد
            else {
                node = temp;
            }
        }

        // يوجد ابنان
        else {

            BookNode successor = findMin(node.getRight());

            node.setBook(successor.getBook());

            node.setRight(delete(node.getRight(),
                                 successor.getBook().getIsbn()));
        }
    }

    // إذا أصبحت الشجرة فارغة
    if (node == null)
        return null;

    // تحديث الارتفاع
    updateHeight(node);

    // حساب التوازن
    int balance = getBalance(node);

    // LL
    if (balance > 1 && getBalance(node.getLeft()) >= 0)
        return rightRotate(node);

    // LR
    if (balance > 1 && getBalance(node.getLeft()) < 0) {
        node.setLeft(leftRotate(node.getLeft()));
        return rightRotate(node);
    }

    // RR
    if (balance < -1 && getBalance(node.getRight()) <= 0)
        return leftRotate(node);

    // RL
    if (balance < -1 && getBalance(node.getRight()) > 0) {
        node.setRight(rightRotate(node.getRight()));
        return leftRotate(node);
    }

    return node;
}

public void printInOrder() {
printInOrder(root);
}

private void printInOrder(BookNode node) {
if (node == null)
   return;

printInOrder(node.getLeft());

System.out.println(node.getBook().getIsbn());

printInOrder(node.getRight());
}
private int height(BookNode node){
if (node==null){
   return 0;
}
else{
   return node.getHeight();
}
}
private void updateHeight(BookNode node){
node.setHeight(1+Math.max(height(node.getLeft()),height(node.getRight())));
}
private int getBalance(BookNode node){
if(node==null) return 0;
return height(node.getLeft())-height(node.getRight());
}
private BookNode rightRotate(BookNode y){
BookNode x = y.getLeft();
BookNode t2 = x.getRight();

x.setRight(y);
y.setLeft(t2);

updateHeight(y);
updateHeight(x);

return x;
}
private BookNode leftRotate(BookNode y){
BookNode x = y.getRight();
BookNode t2 = x.getLeft();

x.setLeft(y);
y.setRight(t2);

updateHeight(y);
updateHeight(x);

return x; 
}
public ArrayList<Book> getAllBooks() {
    ArrayList<Book> list = new ArrayList<>();
    inOrder(root, list);
    return list;
}
private void inOrder(BookNode node, ArrayList<Book> list) {

    if (node == null)
        return;

    inOrder(node.getLeft(), list);

    list.add(node.getBook());

    inOrder(node.getRight(), list);
}



}


