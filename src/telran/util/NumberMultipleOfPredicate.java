package telran.util;

import java.util.function.Predicate;

public class NumberMultipleOfPredicate implements Predicate<Integer> {
private Integer number;


	public NumberMultipleOfPredicate(Integer number) {
	super();
	this.number = number;
}


	@Override
	public boolean test(Integer t) {
		return t % number == 0; 
	}

}
