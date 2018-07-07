/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.renteasy.injector.components;

import com.renteasy.injector.modules.AppModule;
import com.renteasy.service.ProductIntentService;
import com.renteasy.views.activity.CheckOutActivity;
import com.renteasy.views.activity.LoginActivity;
import com.renteasy.views.activity.MainActivity;
import com.renteasy.views.activity.ProductCatalogActivity;
import com.renteasy.views.activity.SplashActivity;
import com.renteasy.views.fragments.AddressFragment;
import com.renteasy.views.fragments.CartFragment;
import com.renteasy.views.fragments.CategoryListFragment;
import com.renteasy.views.fragments.CheckoutFooterFragment;
import com.renteasy.views.fragments.CheckoutFragment;
import com.renteasy.views.fragments.FavouriteFragment;
import com.renteasy.views.fragments.ProductDetailFragment;
import com.renteasy.views.fragments.ProductGridFragment;
import com.renteasy.views.fragments.ProfileFragment;
import com.renteasy.views.fragments.RentedDetailFragment;
import com.renteasy.views.widget.HotDealsFactory;
import com.renteasy.views.widget.HotDealsWidget;

import javax.inject.Singleton;

import dagger.Component;

@Component(dependencies = {}, modules = {AppModule.class})
@Singleton
public interface AppComponent  {
    SplashActivity inject(SplashActivity splashActivity);
    MainActivity inject(MainActivity activity);
    LoginActivity inject(LoginActivity loginActivity);
    CategoryListFragment inject(CategoryListFragment fragment);
    ProductCatalogActivity inject(ProductCatalogActivity productCatalogActivity);
    ProductGridFragment inject(ProductGridFragment productListFragment);
    ProductDetailFragment inject(ProductDetailFragment productDetailFragment);
    CheckoutFooterFragment inject(CheckoutFooterFragment checkoutFooterFragment);
    CartFragment inject(CartFragment cartFragment);
    FavouriteFragment inject(FavouriteFragment favouriteFragment);
    CheckOutActivity inject (CheckOutActivity  checkOutActivity);
    CheckoutFragment inject(CheckoutFragment checkoutFragment);
    ProductIntentService inject(ProductIntentService productIntentService);
    HotDealsWidget inject(HotDealsWidget hotDealsService);
    HotDealsFactory inject(HotDealsFactory hotDealsFactory);
    ProfileFragment inject(ProfileFragment profileFragment);
    AddressFragment inject (AddressFragment addressFragment);
    RentedDetailFragment inject (RentedDetailFragment rentedDetailFragment);
}
