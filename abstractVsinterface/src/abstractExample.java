public class abstractExample {

    public abstract class Animal {

        protected abstract void uniqueBehavior();
        public abstract void eat();
        public abstract void sleep();

    }

    public class Cat extends Animal {

        @Override
        protected void uniqueBehavior() {
            //override method
        }

        @Override
        public void eat() {
         //override method
        }

        @Override
        public void sleep() {
            //override method
        }



    }


}
