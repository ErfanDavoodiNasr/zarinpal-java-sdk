<div dir="rtl" align="right">

# <span dir="ltr">SDK</span> جاوا زرین‌پال

## معرفی

این کتابخانه یک <span dir="ltr">SDK</span> سبک برای اتصال به درگاه پرداخت زرین‌پال در پروژه‌های <span dir="ltr">Spring Boot 3.5.7</span> است. هدف آن ساده‌سازی پرداخت، اعتبارسنجی زودهنگام و دریافت پاسخ‌های تایپ‌شده است.

این پروژه یک کتابخانه است و برنامه اجرایی ندارد؛ آن را در پروژه خود استفاده می‌کنید تا روی منطق کسب‌وکار تمرکز کنید.

---

## پیش‌نیازها

* <span dir="ltr">Java 21</span>
* <span dir="ltr">Spring Boot 3.5.7</span>

---

## نصب

### مرحله ۱: ساخت و نصب محلی

ابتدا کتابخانه را در مخزن محلی <span dir="ltr">Maven</span> نصب کنید:

<div dir="ltr" align="left">

```bash
mvn clean install
```

</div>

### مرحله ۲: افزودن به پروژه مصرف‌کننده

<div dir="ltr" align="left">

```xml
<dependency>
  <groupId>com.ernoxin</groupId>
  <artifactId>zarinpal-java-sdk</artifactId>
  <version>1.1.1</version>
</dependency>
```

</div>

---

## پیکربندی

پیکربندی به‌صورت <span dir="ltr">fail-fast</span> انجام می‌شود: اگر مقدارهای اجباری ناقص باشند، برنامه در زمان بالا آمدن متوقف می‌شود تا خطا به مرحله پرداخت نرسد.

### کلیدهای <span dir="ltr">application.properties</span>

| کلید                                                  | الزامی | پیش‌فرض                                               | توضیح                                                                                           |
| ----------------------------------------------------- | -----: | ----------------------------------------------------- | ----------------------------------------------------------------------------------------------- |
| <span dir="ltr">`zarinpal.merchant-id`</span>         |    بله | -                                                     | شناسه پذیرنده به‌صورت <span dir="ltr">UUID</span> با طول ۳۶ کاراکتر                             |
| <span dir="ltr">`zarinpal.callback-url`</span>        |    بله | -                                                     | آدرس بازگشت پس از پرداخت، باید <span dir="ltr">http</span> یا <span dir="ltr">https</span> باشد |
| <span dir="ltr">`zarinpal.environment`</span>         |    خیر | <span dir="ltr">`PRODUCTION`</span>                   | محیط اجرا: <span dir="ltr">`PRODUCTION`</span> یا <span dir="ltr">`SANDBOX`</span>              |
| <span dir="ltr">`zarinpal.base-url.production`</span> |    خیر | <span dir="ltr">`https://payment.zarinpal.com`</span> | دامنه سرویس در محیط عملیاتی                                                                     |
| <span dir="ltr">`zarinpal.base-url.sandbox`</span>    |    خیر | <span dir="ltr">`https://sandbox.zarinpal.com`</span> | دامنه سرویس در محیط تست                                                                         |
| <span dir="ltr">`zarinpal.operation-version`</span>   |    خیر | <span dir="ltr">`v4`</span>                           | نسخه مسیر <span dir="ltr">API</span>                                                            |
| <span dir="ltr">`zarinpal.timeout.connect`</span>     |    خیر | <span dir="ltr">`10s`</span>                          | مهلت اتصال                                                                                      |
| <span dir="ltr">`zarinpal.timeout.read`</span>        |    خیر | <span dir="ltr">`30s`</span>                          | مهلت دریافت پاسخ                                                                                |
| <span dir="ltr">`zarinpal.retry.enabled`</span>       |    خیر | <span dir="ltr">`false`</span>                        | فعال‌سازی تلاش مجدد در خطاهای شبکه                                                              |
| <span dir="ltr">`zarinpal.retry.max-attempts`</span>  |    خیر | <span dir="ltr">`1`</span>                            | تعداد تلاش‌ها در صورت فعال بودن <span dir="ltr">`retry`</span>                                  |
| <span dir="ltr">`zarinpal.retry.backoff`</span>       |    خیر | <span dir="ltr">`0ms`</span>                          | وقفه بین تلاش‌ها                                                                                |
| <span dir="ltr">`zarinpal.http.user-agent`</span>     |    خیر | <span dir="ltr">`ZarinpalJavaSdk`</span>              | مقدار <span dir="ltr">User-Agent</span> در درخواست‌ها                                           |
| <span dir="ltr">`zarinpal.max-amount-irt`</span>      |    خیر | <span dir="ltr">`100000000`</span>                    | سقف مبلغ برای پرداخت با تومان (<span dir="ltr">IRT</span>)                                      |
| <span dir="ltr">`zarinpal.max-amount-irr`</span>      |    خیر | <span dir="ltr">`1000000000`</span>                   | سقف مبلغ برای پرداخت با ریال (<span dir="ltr">IRR</span>)                                       |
| <span dir="ltr">`zarinpal.min-wage-amount`</span>     |    خیر | <span dir="ltr">`10000`</span>                        | حداقل مبلغ هر تسهیم در تسویه اشتراکی (ریال)                                                     |

محدودیت‌های مبلغ و تسهیم (مثل <span dir="ltr">`zarinpal.max-amount-irt`</span>، <span dir="ltr">`zarinpal.max-amount-irr`</span> و <span dir="ltr">`zarinpal.min-wage-amount`</span>) توسط <span dir="ltr">SDK</span> برای اعتبارسنجی استفاده می‌شوند و درگاه نیز محدودیت‌های خودش را اعمال می‌کند؛ در صورت تغییر این مقادیر، فقط تنظیمات را به‌روزرسانی کنید و نیازی به تغییر کد نیست.

### <span dir="ltr">timeout</span> و <span dir="ltr">retry</span>

* <span dir="ltr">`zarinpal.timeout.connect`</span> زمان برقراری اتصال است و شامل <span dir="ltr">TCP/SSL</span> می‌شود.
* <span dir="ltr">`zarinpal.timeout.read`</span> زمان انتظار برای دریافت پاسخ پس از اتصال است.
* <span dir="ltr">retry</span> فقط روی خطاهای شبکه/ارتباطی فعال می‌شود و روی خطاهای منطقی درگاه یا کدهای پاسخ اجرا نمی‌شود.
* <span dir="ltr">`max-attempts`</span> تعداد کل تلاش‌ها و <span dir="ltr">`backoff`</span> فاصله بین تلاش‌ها را مشخص می‌کند.
* **هشدار:** فعال کردن <span dir="ltr">retry</span> برای <span dir="ltr">requestPayment</span> ممکن است باعث ایجاد چند <span dir="ltr">authority</span> شود اگر درخواست اول در درگاه ثبت شده ولی پاسخ آن به شما نرسیده باشد.

فرمت مدت‌زمان‌ها می‌تواند به صورت <span dir="ltr">`500ms`</span>، <span dir="ltr">`2s`</span> یا <span dir="ltr">`1m`</span> باشد.

### نمونه تنظیمات

حداقل تنظیمات لازم:

<div dir="ltr" align="left">

```properties
zarinpal.merchant-id=11111111-1111-1111-1111-111111111111
zarinpal.callback-url=https://example.com/payment/callback
```

</div>

محیط تست:

<div dir="ltr" align="left">

```properties
zarinpal.merchant-id=11111111-1111-1111-1111-111111111111
zarinpal.callback-url=https://example.com/payment/callback
zarinpal.environment=SANDBOX
```

</div>

---

## آموزش گام‌به‌گام پرداخت

1. تزریق کلاینت
2. ساخت درخواست پرداخت
3. انتقال خریدار به درگاه
4. بازگشت و وریفای پرداخت

### گام ۱: تزریق کلاینت

کلاینت به‌صورت خودکار ساخته می‌شود و کافی است آن را تزریق کنید:

<div dir="ltr" align="left">

```java
import com.ernoxin.zarinpaljavasdk.client.ZarinpalClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ZarinpalClient client;

}
```

</div>

### گام ۲: ساخت درخواست پرداخت

حداقل ورودی لازم شامل مبلغ و توضیح است. اگر <span dir="ltr">callback-url</span> در تنظیمات تعریف شده باشد، ارسال آن در هر درخواست ضروری نیست.

اگر برای یک تراکنش خاص آدرس بازگشت متفاوتی دارید، مقدار <span dir="ltr">callbackUrl</span> را در همان درخواست تنظیم کنید.

<div dir="ltr" align="left">

```java
PaymentMetadata metadata = new PaymentMetadata(
        "09121234567",
        "info@example.com",
        "ORDER-1001",
        true,
        null
);

PaymentRequest request = PaymentRequest.builder(150000, "خرید اشتراک ماهانه")
        .currency(ZarinpalCurrency.IRR)
        .metadata(metadata)
        .build();

PaymentRequestResult result = client.requestPayment(request);
```

</div>

### گام ۳: انتقال خریدار به درگاه

<div dir="ltr" align="left">

```java
String redirectUrl = client.buildRedirectUrl(result.authority());
```

</div>

### گام ۴: بازگشت و وریفای پرداخت

فقط وقتی <span dir="ltr">Status</span> برابر <span dir="ltr">OK</span> است باید وریفای انجام شود و مقدار <span dir="ltr">amount</span> باید همان مبلغ تراکنش باشد.

<div dir="ltr" align="left">

```java
public VerifyResult handleCallback(ZarinpalClient client, Map<String, String> params, long amount) {
    ZarinpalCallback callback = client.parseCallback(params);
    if (!callback.isOk()) {
        throw new IllegalStateException("پرداخت ناموفق یا لغو شده است");
    }
    VerifyRequest request = new VerifyRequest(amount, callback.authority());
    return client.verifyPayment(request);
}
```

</div>

<span dir="ltr">parseCallback</span> کلیدهای <span dir="ltr">Authority</span> و <span dir="ltr">Status</span> را به‌صورت <span dir="ltr">case-insensitive</span> می‌خواند؛ اگر هرکدام ارسال نشده باشند یا مقدار معتبر نداشته باشند، <span dir="ltr">ZarinpalCallbackException</span> دریافت می‌کنید. ورودی می‌تواند از نوع <span dir="ltr">Map</span> یا <span dir="ltr">MultiValueMap</span> باشد.

---

## مدل‌ها و اعتبارسنجی

همه متدها <span dir="ltr">merchant_id</span> را از تنظیمات می‌خوانند و در ورودی‌ها دریافت نمی‌کنند. در صورت نامعتبر بودن داده‌ها، خطای <span dir="ltr">ZarinpalValidationException</span> قبل از ارسال درخواست رخ می‌دهد.

### <span dir="ltr">PaymentRequest</span>

| فیلد (SDK)                           | نوع                                      |           الزامی | توضیح                                                                                                                          |
| ------------------------------------ | ---------------------------------------- | ---------------: | ------------------------------------------------------------------------------------------------------------------------------ |
| <span dir="ltr">`amount`</span>      | <span dir="ltr">long</span>              |              بله | مبلغ تراکنش؛ باید مثبت باشد. سقف مبلغ بر اساس واحد پولی از تنظیمات خوانده می‌شود.                                              |
| <span dir="ltr">`description`</span> | <span dir="ltr">String</span>            |              بله | الزامی و حداکثر ۵۰۰ کاراکتر.                                                                                                   |
| <span dir="ltr">`callbackUrl`</span> | <span dir="ltr">URI</span>               | خیر (در درخواست) | اگر مقدار ندهید از <span dir="ltr">`zarinpal.callback-url`</span> استفاده می‌شود؛ باید <span dir="ltr">http/https</span> باشد. |
| <span dir="ltr">`currency`</span>    | <span dir="ltr">ZarinpalCurrency</span>  |              خیر | <span dir="ltr">IRR</span> یا <span dir="ltr">IRT</span>؛ در صورت عدم ارسال، رفتار پیش‌فرض درگاه اعمال می‌شود.                 |
| <span dir="ltr">`referrerId`</span>  | <span dir="ltr">String</span>            |              خیر | کد معرف؛ در صورت ارسال نباید خالی باشد.                                                                                        |
| <span dir="ltr">`metadata`</span>    | <span dir="ltr">PaymentMetadata</span>   |              خیر | اطلاعات تکمیلی خریدار و تنظیمات رفتاری تراکنش.                                                                                 |
| <span dir="ltr">`cartData`</span>    | <span dir="ltr">CartData</span>          |              خیر | جزئیات سبد خرید؛ در صورت ارسال، <span dir="ltr">`items`</span> باید وجود داشته باشد.                                           |
| <span dir="ltr">`wages`</span>       | <span dir="ltr">List<PaymentWage></span> |              خیر | تسویه اشتراکی بین شرکا.                                                                                                        |

نام فیلدها در خروجی <span dir="ltr">JSON</span> به صورت <span dir="ltr">snake_case</span> ارسال می‌شود و مقادیر <span dir="ltr">null</span> حذف می‌شوند؛ بنابراین هر فیلدی را مقداردهی نکنید به درگاه ارسال نخواهد شد (مثلا <span dir="ltr">`callbackUrl`</span> به <span dir="ltr">`callback_url`</span> تبدیل می‌شود).

### <span dir="ltr">PaymentMetadata</span>

| فیلد (SDK)                          | نوع                            | الزامی | توضیح                                                                                                                                                                                     |
| ----------------------------------- | ------------------------------ | -----: | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| <span dir="ltr">`mobile`</span>     | <span dir="ltr">String</span>  |    خیر | شماره موبایل خریدار؛ در صورت ارسال نباید خالی باشد.                                                                                                                                       |
| <span dir="ltr">`email`</span>      | <span dir="ltr">String</span>  |    خیر | ایمیل خریدار؛ در صورت ارسال نباید خالی باشد.                                                                                                                                              |
| <span dir="ltr">`orderId`</span>    | <span dir="ltr">String</span>  |    خیر | شناسه سفارش؛ در صورت ارسال نباید خالی باشد.                                                                                                                                               |
| <span dir="ltr">`autoVerify`</span> | <span dir="ltr">Boolean</span> |    خیر | اگر <span dir="ltr">true</span> باشد وریفای خودکار انجام می‌شود؛ اگر <span dir="ltr">false</span> باشد باید وریفای را خودتان فراخوانی کنید؛ اگر ارسال نشود رفتار بر اساس تنظیمات پنل است. |
| <span dir="ltr">`cardPan`</span>    | <span dir="ltr">String</span>  |    خیر | برای محدود کردن پرداخت به یک کارت مشخص؛ باید ۱۶ کاراکتر از ارقام یا <span dir="ltr">X/x</span> باشد.                                                                                      |

### <span dir="ltr">CartData</span>

اگر <span dir="ltr">cart_data</span> ارسال شود، آرایه <span dir="ltr">items</span> الزامی است و نباید خالی باشد. <span dir="ltr">SDK</span> جمع کل سبد خرید را با <span dir="ltr">amount</span> تطبیق نمی‌دهد و فقط ساختار و قوانین هر آیتم را بررسی می‌کند.

#### آیتم‌ها (<span dir="ltr">items</span>)

| فیلد (SDK)                             | نوع                           | الزامی | توضیح                                                                                      |
| -------------------------------------- | ----------------------------- | -----: | ------------------------------------------------------------------------------------------ |
| <span dir="ltr">`itemName`</span>      | <span dir="ltr">String</span> |    بله | نام محصول یا خدمت؛ نباید خالی باشد.                                                        |
| <span dir="ltr">`itemAmount`</span>    | <span dir="ltr">long</span>   |    بله | مبلغ هر واحد؛ باید مثبت باشد.                                                              |
| <span dir="ltr">`itemCount`</span>     | <span dir="ltr">long</span>   |    بله | تعداد واحدها؛ باید مثبت باشد.                                                              |
| <span dir="ltr">`itemAmountSum`</span> | <span dir="ltr">long</span>   |    بله | مجموع مبلغ این آیتم؛ باید دقیقاً برابر <span dir="ltr">itemAmount * itemCount</span> باشد. |

#### هزینه‌های اضافی (<span dir="ltr">added_costs</span>)

| فیلد (SDK)                         | نوع                         | الزامی | توضیح                                                    |
| ---------------------------------- | --------------------------- | -----: | -------------------------------------------------------- |
| <span dir="ltr">`tax`</span>       | <span dir="ltr">Long</span> |    خیر | مالیات سبد خرید؛ در صورت ارسال باید غیرمنفی باشد.        |
| <span dir="ltr">`payment`</span>   | <span dir="ltr">Long</span> |    خیر | کارمزد یا هزینه پرداخت؛ در صورت ارسال باید غیرمنفی باشد. |
| <span dir="ltr">`transport`</span> | <span dir="ltr">Long</span> |    خیر | هزینه حمل یا ارسال؛ در صورت ارسال باید غیرمنفی باشد.     |

#### کسرها (<span dir="ltr">deductions</span>)

| فیلد (SDK)                        | نوع                         | الزامی | توضیح                                        |
| --------------------------------- | --------------------------- | -----: | -------------------------------------------- |
| <span dir="ltr">`discount`</span> | <span dir="ltr">Long</span> |    خیر | مبلغ تخفیف؛ در صورت ارسال باید غیرمنفی باشد. |

### <span dir="ltr">PaymentWage</span> (تسویه اشتراکی)

| فیلد (SDK)                           | نوع                           | الزامی | توضیح                                                                                                        |
| ------------------------------------ | ----------------------------- | -----: | ------------------------------------------------------------------------------------------------------------ |
| <span dir="ltr">`iban`</span>        | <span dir="ltr">String</span> |    بله | شماره شبا ۲۶ کاراکتری که با <span dir="ltr">IR</span> شروع می‌شود.                                           |
| <span dir="ltr">`amount`</span>      | <span dir="ltr">long</span>   |    بله | مبلغ تسهیم؛ باید مثبت باشد و حداقل مقدار از <span dir="ltr">`zarinpal.min-wage-amount`</span> خوانده می‌شود. |
| <span dir="ltr">`description`</span> | <span dir="ltr">String</span> |    بله | توضیح تسهیم؛ نباید خالی باشد.                                                                                |

قوانین تسویه اشتراکی: اگر <span dir="ltr">wages</span> ارسال شود نباید خالی باشد، حداکثر ۵ آیتم مجاز است، مجموع تسهیم حداکثر ۹۹٪ مبلغ کل است و هر آیتم باید شماره شبا و مقدار معتبر داشته باشد.

---

## سناریوهای رایج

نمونه‌های کوتاه برای نیازهای متداول:

### پرداخت با واحد پولی تومان

<div dir="ltr" align="left">

```java
PaymentRequest request = PaymentRequest.builder(10000, "افزایش اعتبار")
        .currency(ZarinpalCurrency.IRT)
        .build();

PaymentRequestResult result = client.requestPayment(request);
```

</div>

### آدرس بازگشت اختصاصی برای یک تراکنش

<div dir="ltr" align="left">

```java
PaymentMetadata metadata = new PaymentMetadata(
        "09121234567",
        "info@example.com",
        "ORDER-3001",
        null,
        null
);

PaymentRequest request = PaymentRequest.builder(120000, "پرداخت سفارش 3001")
        .callbackUrl(URI.create("https://example.com/payment/callback"))
        .metadata(metadata)
        .build();

PaymentRequestResult result = client.requestPayment(request);
```

</div>

### کنترل رفتار وریفای با <span dir="ltr">metadata.auto_verify</span>

<div dir="ltr" align="left">

```java
PaymentMetadata metadata = new PaymentMetadata(
        "09121234567",
        "info@example.com",
        "ORDER-2002",
        false,
        null
);

PaymentRequest request = PaymentRequest.builder(50000, "خرید کالا")
        .metadata(metadata)
        .build();

PaymentRequestResult result = client.requestPayment(request);
```

</div>

### ارسال سبد خرید با <span dir="ltr">cart_data</span>

<div dir="ltr" align="left">

```java
CartData cartData = new CartData(
        List.of(
                new CartItem("کفش ورزشی", 50000, 2, 100000),
                new CartItem("جوراب", 25000, 1, 25000)
        ),
        new CartAddedCosts(5000L, 1000L, 2000L),
        new CartDeductions(3000L)
);

PaymentRequest request = PaymentRequest.builder(150000, "پرداخت سفارش 1010")
        .cartData(cartData)
        .build();

PaymentRequestResult result = client.requestPayment(request);
```

</div>

### تسویه اشتراکی با <span dir="ltr">wages</span>

<div dir="ltr" align="left">

```java
List<PaymentWage> wages = List.of(
        new PaymentWage("IR123456789123456478945165", 10000, "سهم شریک اول"),
        new PaymentWage("IR567891234564789451651234", 50000, "سهم شریک دوم")
);

PaymentRequest request = PaymentRequest.builder(200000, "تسویه اشتراکی")
        .wages(wages)
        .build();

PaymentRequestResult result = client.requestPayment(request);
```

</div>

### پرداخت با کارت مشخص

<div dir="ltr" align="left">

```java
PaymentMetadata metadata = new PaymentMetadata(
        "09121234567",
        "info@example.com",
        null,
        null,
        "502229XXXXXX1234"
);

PaymentRequest request = PaymentRequest.builder(10000, "پرداخت با کارت مشخص")
        .metadata(metadata)
        .build();

PaymentRequestResult result = client.requestPayment(request);
```

</div>

### همکاری در فروش با <span dir="ltr">referrer_id</span>

<div dir="ltr" align="left">

```java
PaymentRequest request = PaymentRequest.builder(70000, "پرداخت با کد معرف")
        .referrerId("REF-CODE-123")
        .build();

PaymentRequestResult result = client.requestPayment(request);
```

</div>

### ذخیره کارت در درگاه با ارسال شماره موبایل

<div dir="ltr" align="left">

```java
PaymentMetadata metadata = new PaymentMetadata(
        "09120000000",
        "user@example.com",
        null,
        null,
        null
);

PaymentRequest request = PaymentRequest.builder(10000, "پرداخت با ذخیره کارت")
        .metadata(metadata)
        .build();

PaymentRequestResult result = client.requestPayment(request);
```

</div>

---

## تمام متدهای کلاینت

| متد                                         | ورودی                                                              | خروجی                                       | نکته مهم                                                                               |
| ------------------------------------------- | ------------------------------------------------------------------ | ------------------------------------------- | -------------------------------------------------------------------------------------- |
| <span dir="ltr">`requestPayment`</span>     | <span dir="ltr">PaymentRequest</span>                              | <span dir="ltr">PaymentRequestResult</span> | ایجاد تراکنش و دریافت <span dir="ltr">authority</span>                                 |
| <span dir="ltr">`buildRedirectUrl`</span>   | <span dir="ltr">authority</span>                                   | <span dir="ltr">String</span>               | آدرس نهایی از دامنه محیط و مسیر <span dir="ltr">`/pg/StartPay/`</span> ساخته می‌شود    |
| <span dir="ltr">`parseCallback`</span>      | <span dir="ltr">Map</span> یا <span dir="ltr">MultiValueMap</span> | <span dir="ltr">ZarinpalCallback</span>     | خروجی فقط شامل <span dir="ltr">authority</span> و <span dir="ltr">status</span> است    |
| <span dir="ltr">`verifyPayment`</span>      | <span dir="ltr">VerifyRequest</span>                               | <span dir="ltr">VerifyResult</span>         | کد <span dir="ltr">100</span> موفق و کد <span dir="ltr">101</span> قبلا وریفای شده است |
| <span dir="ltr">`reversePayment`</span>     | <span dir="ltr">ReverseRequest</span>                              | <span dir="ltr">ReverseResult</span>        | فقط تراکنش موفق در ۳۰ دقیقه اول و نیازمند ثبت آی‌پی سرور در پنل                        |
| <span dir="ltr">`inquirePayment`</span>     | <span dir="ltr">InquiryRequest</span>                              | <span dir="ltr">InquiryResult</span>        | فقط وضعیت را اعلام می‌کند و جایگزین وریفای نیست                                        |
| <span dir="ltr">`unverifiedPayments`</span> | -                                                                  | <span dir="ltr">UnverifiedResult</span>     | حداکثر ۱۰۰ تراکنش موفق وریفای‌نشده برگردانده می‌شود                                    |
| <span dir="ltr">`calculateFee`</span>       | <span dir="ltr">FeeCalculationRequest</span>                       | <span dir="ltr">FeeCalculationResult</span> | فقط محاسبه کارمزد؛ مبلغ حداقل ۱۰۰۰ ریال                                                |

---

## سرویس تست (<span dir="ltr">Sandbox</span>)

* برای تست، مقدار <span dir="ltr">`zarinpal.environment`</span> را برابر <span dir="ltr">`SANDBOX`</span> قرار دهید.
* در سندباکس، <span dir="ltr">authority</span> با حرف <span dir="ltr">S</span> شروع می‌شود.
* در سندباکس، <span dir="ltr">merchant-id</span> می‌تواند هر <span dir="ltr">UUID</span> معتبر باشد.

---

## خطاها و <span dir="ltr">Exception</span>ها

### لیست کدهای درگاه


| حوزه                                  |                         کد | پیام انگلیسی                                                                                              | شرح فارسی                                                |
| ------------------------------------- | -------------------------: | --------------------------------------------------------------------------------------------------------- | -------------------------------------------------------- |
| <span dir="ltr">PaymentReverse</span> | <span dir="ltr">-63</span> | <span dir="ltr">Maximum time for reverse this session is expired.</span>                                  | بازه ۳۰ دقیقه‌ای ریورس منقضی شده است                     |
| <span dir="ltr">PaymentReverse</span> | <span dir="ltr">-62</span> | <span dir="ltr">Terminal ip limit most be active.</span>                                                  | آی‌پی سرور در پنل ثبت نشده است                           |
| <span dir="ltr">PaymentReverse</span> | <span dir="ltr">-61</span> | <span dir="ltr">Session is not in success status.</span>                                                  | تراکنش موفق نیست یا قبلا ریورس شده است                   |
| <span dir="ltr">PaymentReverse</span> | <span dir="ltr">-60</span> | <span dir="ltr">Session can not be reversed with bank.</span>                                             | امکان ریورس با بانک وجود ندارد                           |
| <span dir="ltr">PaymentVerify</span>  | <span dir="ltr">-55</span> | <span dir="ltr">Manual payment request not found.</span>                                                  | تراکنش یافت نشد                                          |
| <span dir="ltr">PaymentVerify</span>  | <span dir="ltr">-54</span> | <span dir="ltr">Invalid authority.</span>                                                                 | اتوریتی نامعتبر است                                      |
| <span dir="ltr">PaymentVerify</span>  | <span dir="ltr">-53</span> | <span dir="ltr">Session is not this merchant_id session</span>                                            | پرداخت متعلق به این مرچنت نیست                           |
| <span dir="ltr">PaymentVerify</span>  | <span dir="ltr">-52</span> | <span dir="ltr">Oops!!, please contact our support team</span>                                            | خطای غیر منتظره                                          |
| <span dir="ltr">PaymentVerify</span>  | <span dir="ltr">-51</span> | <span dir="ltr">Session is not valid, session is not active paid try.</span>                              | پرداخت ناموفق است                                        |
| <span dir="ltr">PaymentVerify</span>  | <span dir="ltr">-50</span> | <span dir="ltr">Session is not valid, amounts values is not the same.</span>                              | مبلغ وریفای با مبلغ پرداختی متفاوت است                   |
| <span dir="ltr">PaymentRequest</span> | <span dir="ltr">-41</span> | <span dir="ltr">Maximum amount is 100,000,000 tomans.</span>                                              | حداکثر مبلغ ۱۰۰٬۰۰۰٬۰۰۰ تومان است                        |
| <span dir="ltr">PaymentRequest</span> | <span dir="ltr">-40</span> | <span dir="ltr">Invalid extra params, expire_in is not valid.</span>                                      | پارامتر اضافی نامعتبر است                                |
| <span dir="ltr">PaymentRequest</span> | <span dir="ltr">-39</span> | <span dir="ltr">Wages have a error!</span>                                                                | خطا در تسهیم                                             |
| <span dir="ltr">PaymentRequest</span> | <span dir="ltr">-38</span> | <span dir="ltr">Wages need to set Iban in shaparak.</span>                                                | عدم تعریف صحیح شبا در شاپرک                              |
| <span dir="ltr">PaymentRequest</span> | <span dir="ltr">-37</span> | <span dir="ltr">One or more iban entered for wages(floating) from the bank side are inactive.</span>      | یک یا چند شماره شبا غیرفعال است                          |
| <span dir="ltr">PaymentRequest</span> | <span dir="ltr">-36</span> | <span dir="ltr">The minimum amount for wages(floating) should be 10,000 Rials</span>                      | حداقل مبلغ هر تسهیم ۱۰٬۰۰۰ ریال است                      |
| <span dir="ltr">PaymentRequest</span> | <span dir="ltr">-35</span> | <span dir="ltr">Wages is not valid, Total wages(floating) has been reached the limit in max parts.</span> | تعداد دریافت‌کنندگان بیش از حد مجاز است                  |
| <span dir="ltr">PaymentRequest</span> | <span dir="ltr">-34</span> | <span dir="ltr">Wages is not valid, Total wages(fixed) has been overload max amount.</span>               | مجموع تسهیم از مبلغ کل بیشتر است                         |
| <span dir="ltr">PaymentRequest</span> | <span dir="ltr">-33</span> | <span dir="ltr">Wages floating is not valid.</span>                                                       | درصدهای وارد شده صحیح نیست                               |
| <span dir="ltr">PaymentRequest</span> | <span dir="ltr">-32</span> | <span dir="ltr">Wages is not valid, Total wages(floating) has been overload max amount.</span>            | مجموع تسهیم از مبلغ کل بیشتر است                         |
| <span dir="ltr">PaymentRequest</span> | <span dir="ltr">-31</span> | <span dir="ltr">Terminal do not allow to accept wages, please add default bank account in panel.</span>   | حساب بانکی تسویه در پنل ثبت نشده است                     |
| <span dir="ltr">PaymentRequest</span> | <span dir="ltr">-30</span> | <span dir="ltr">Terminal do not allow to accept floating wages.</span>                                    | ترمینال اجازه تسویه اشتراکی شناور ندارد                  |
| <span dir="ltr">public</span>         | <span dir="ltr">-19</span> | <span dir="ltr">Terminal user transactions are banned.</span>                                             | امکان ایجاد تراکنش برای این ترمینال وجود ندارد           |
| <span dir="ltr">public</span>         | <span dir="ltr">-18</span> | <span dir="ltr">The referrer address does not match the registered domain.</span>                         | امکان استفاده کد درگاه اختصاصی روی دامنه دیگر وجود ندارد |
| <span dir="ltr">public</span>         | <span dir="ltr">-17</span> | <span dir="ltr">Terminal user level is not valid : ( please contact our support team).</span>             | محدودیت پذیرنده در سطح آبی                               |
| <span dir="ltr">public</span>         | <span dir="ltr">-16</span> | <span dir="ltr">Terminal user level is not valid : ( please contact our support team).</span>             | سطح تایید پایین‌تر از نقره‌ای است                        |
| <span dir="ltr">public</span>         | <span dir="ltr">-15</span> | <span dir="ltr">Terminal user is suspend : (please contact our support team).</span>                      | درگاه تعلیق شده است                                      |
| <span dir="ltr">public</span>         | <span dir="ltr">-14</span> | <span dir="ltr">The callback URL domain does not match the registered terminal domain.</span>             | دامنه کال‌بک با دامنه ثبت‌شده مغایرت دارد                |
| <span dir="ltr">public</span>         | <span dir="ltr">-13</span> | <span dir="ltr">Terminal limit reached.</span>                                                            | محدودیت تراکنش                                           |
| <span dir="ltr">public</span>         | <span dir="ltr">-12</span> | <span dir="ltr">To many attempts, please try again later.</span>                                          | تلاش بیش از حد مجاز                                      |
| <span dir="ltr">public</span>         | <span dir="ltr">-11</span> | <span dir="ltr">Terminal is not active, please contact our support team.</span>                           | مرچنت کد فعال نیست                                       |
| <span dir="ltr">public</span>         | <span dir="ltr">-10</span> | <span dir="ltr">Terminal is not valid, please check merchant_id or ip address.</span>                     | مرچنت کد یا آی‌پی معتبر نیست                             |
| <span dir="ltr">public</span>         |  <span dir="ltr">-9</span> | <span dir="ltr">Validation error</span>                                                                   | خطای اعتبارسنجی                                          |
| <span dir="ltr">public</span>         |  <span dir="ltr">-5</span> | <span dir="ltr">Referrer ID is invalid</span>                                                             | کد معرف نامعتبر است                                      |
| <span dir="ltr">public</span>         |  <span dir="ltr">-4</span> | <span dir="ltr">Amount is invalid</span>                                                                  | مبلغ خارج از محدوده مجاز است                             |
| <span dir="ltr">public</span>         |  <span dir="ltr">-3</span> | <span dir="ltr">Description is required or too long</span>                                                | توضیحات وارد نشده یا بیشتر از ۵۰۰ کاراکتر است            |
| <span dir="ltr">public</span>         |  <span dir="ltr">-2</span> | <span dir="ltr">Callback URL is required</span>                                                           | آدرس بازگشت وارد نشده است                                |
| <span dir="ltr">public</span>         | <span dir="ltr">100</span> | <span dir="ltr">Success</span>                                                                            | عملیات موفق                                              |
| <span dir="ltr">PaymentVerify</span>  | <span dir="ltr">101</span> | <span dir="ltr">Verified</span>                                                                           | تراکنش قبلا وریفای شده است                               |
| <span dir="ltr">public</span>         | <span dir="ltr">429</span> | <span dir="ltr">Referrer code format is invalid.</span>                                                   | قالب کد معرف نامعتبر است                                 |

### <span dir="ltr">Exception</span>های <span dir="ltr">SDK</span>

* <span dir="ltr">`ZarinpalValidationException`</span>: ورودی با قوانین معتبر نیست و قبل از ارسال به درگاه خطا گرفته می‌شود
* <span dir="ltr">`ZarinpalApiException`</span>: درگاه پاسخ خطا داده است و شامل کد و پیام درگاه است
* <span dir="ltr">`ZarinpalTransportException`</span>: خطای شبکه یا تایم‌اوت در ارتباط با درگاه
* <span dir="ltr">`ZarinpalCallbackException`</span>: پارامترهای بازگشتی ناقص یا نامعتبر هستند

برای خطایابی شبکه، ابتدا <span dir="ltr">base-url</span>ها را بررسی کنید، زمان‌های <span dir="ltr">timeout</span> را افزایش دهید و در محیط <span dir="ltr">SANDBOX</span> تست کنید.

---

## پرسش‌های پرتکرار

* چرا درخواست پرداخت خطای اعتبارسنجی می‌دهد؟ بررسی کنید <span dir="ltr">merchant-id</span> و <span dir="ltr">callback-url</span> مقدار دارند و توضیح تراکنش خالی نیست.
* چرا وریفای خطای <span dir="ltr">-54</span> می‌دهد؟ مقدار <span dir="ltr">authority</span> نامعتبر است یا اشتباه ارسال شده است.
* چرا ریورس خطای <span dir="ltr">-62</span> می‌دهد؟ آی‌پی سرور در پنل ثبت نشده است.
* چرا پرداخت موفق بوده اما کد <span dir="ltr">101</span> می‌گیرم؟ تراکنش قبلا وریفای شده است.
* چرا <span dir="ltr">referrer_id</span> اعمال نمی‌شود؟ ممکن است کد معرف معتبر نباشد یا شرایط ترمینال اجازه ثبت آن را ندهد.

</div>
