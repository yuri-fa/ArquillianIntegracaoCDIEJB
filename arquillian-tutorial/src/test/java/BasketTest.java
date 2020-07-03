import javax.ejb.EJB;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class BasketTest {

	@Deployment
	public static JavaArchive init() {
		JavaArchive jar = ShrinkWrap.create(JavaArchive.class, "teste.jar")
				.addClasses(Basket.class, SingletonOrderRepository.class, OrderRepository.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		return jar;
	}

	@Inject
	Basket basket;

	@EJB
	OrderRepository orderRepository;

	@Test
	@InSequence(1)
	public void lasqueira() {
		basket.addItem("sunglasses");
		basket.addItem("suit");
		basket.placeOrder();
		Assert.assertEquals(1, orderRepository.getOrderCount());
		Assert.assertEquals(0, basket.getItemCount());

		basket.addItem("raygun");
		basket.addItem("spaceship");
		basket.placeOrder();
		Assert.assertEquals(2, orderRepository.getOrderCount());
		Assert.assertEquals(0, basket.getItemCount());
	}
	
	@Test
	@InSequence(2)
	public void lasqueira2() {
		Assert.assertEquals(2, orderRepository.getOrderCount());
	}

}
