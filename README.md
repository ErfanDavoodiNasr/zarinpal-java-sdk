<div dir="rtl">

<h1>SDK جاوا زرین‌پال</h1>

<p>این SDK برای تیم‌هایی طراحی شده است که می‌خواهند اتصال به درگاه زرین‌پال را در یک پروژه Spring Boot با کمترین بویلرپلیت انجام دهند. فلسفه طراحی آن <b>Configuration-first</b> است: تمام تنظیمات از طریق <code>application.properties</code> یا <code>application.yml</code> انجام می‌شود، و اگر مقادیر  ناقص باشند، برنامه در لحظه استارت به‌صورت <b>fail-fast</b> متوقف می‌شود تا خطا به مرحله درخواست پرداخت نرسد.</p>

<p>پس از تنظیم properties، کافی است <code>ZarinpalClient</code> را تزریق کنید و از آن استفاده کنید.</p>

<h2>پیش‌نیازها</h2>
<ul>
  <li>Java 21</li>
  <li>Spring Boot 4.0.1</li>
</ul>

<h2>نصب بدون انتشار در Maven Central</h2>

<h3>مرحله ۱: نصب محلی SDK</h3>
<pre dir="ltr"><code class="language-bash">mvn clean install</code></pre>

<h3>مرحله ۲: استفاده در پروژه مصرف‌کننده</h3>
<pre dir="ltr"><code class="language-xml">&lt;dependency&gt;
  &lt;groupId&gt;com.ernoxin&lt;/groupId&gt;
  &lt;artifactId&gt;ZarinPal-java-SDK&lt;/artifactId&gt;
  &lt;version&gt;1.0.0&lt;/version&gt;
&lt;/dependency&gt;
</code></pre>

<h2>پیکربندی (Configuration-first)</h2>

<p>دو مقدار اجباری هستند و در صورت عدم وجود یا خالی بودن باعث خطای استارت می‌شوند: <code>zarinpal.merchant-id</code> و <code>zarinpal.callback-url</code>. سایر گزینه‌ها اختیاری‌اند و دارای مقدار پیش‌فرض هستند.</p>

<table>
  <thead>
    <tr>
      <th>کلید</th>
      <th>الزامی</th>
      <th>پیش‌فرض</th>
      <th>توضیح</th>
    </tr>
  </thead>
  <tbody>
    <tr><td>zarinpal.merchant-id</td><td>بله</td><td>-</td><td>شناسه پذیرنده (UUID)</td></tr>
    <tr><td>zarinpal.callback-url</td><td>بله</td><td>-</td><td>آدرس بازگشت درگاه با http/https</td></tr>
    <tr><td>zarinpal.environment</td><td>خیر</td><td>PRODUCTION</td><td>محیط اجرا: PRODUCTION یا SANDBOX</td></tr>
    <tr><td>zarinpal.base-url.production</td><td>خیر</td><td>https://payment.zarinpal.com</td><td>دامنه API و StartPay در محیط عملیاتی</td></tr>
    <tr><td>zarinpal.base-url.sandbox</td><td>خیر</td><td>https://sandbox.zarinpal.com</td><td>دامنه API و StartPay در محیط تست</td></tr>
    <tr><td>zarinpal.operation-version</td><td>خیر</td><td>v4</td><td>نسخه مسیر عملیات API</td></tr>
    <tr><td>zarinpal.timeout.connect</td><td>خیر</td><td>10s</td><td>مهلت اتصال</td></tr>
    <tr><td>zarinpal.timeout.read</td><td>خیر</td><td>30s</td><td>مهلت خواندن پاسخ</td></tr>
    <tr><td>zarinpal.retry.enabled</td><td>خیر</td><td>false</td><td>فعال‌سازی تلاش مجدد در خطاهای شبکه</td></tr>
    <tr><td>zarinpal.retry.max-attempts</td><td>خیر</td><td>1</td><td>تعداد تلاش‌ها در صورت فعال بودن retry</td></tr>
    <tr><td>zarinpal.retry.backoff</td><td>خیر</td><td>0ms</td><td>وقفه بین تلاش‌ها</td></tr>
    <tr><td>zarinpal.http.user-agent</td><td>خیر</td><td>ZarinpalJavaSdk</td><td>User-Agent ارسال‌شده به درگاه</td></tr>
  </tbody>
</table>

<p><b>نکته:</b> مقادیری مثل <code>merchant-id</code>، <code>callback-url</code> و <code>environment</code> فقط از فایل تنظیمات خوانده می‌شوند و نیاز نیست در متدها پاس داده شوند. فقط اگر می‌خواهید برای یک درخواست خاص <code>callbackUrl</code> متفاوتی داشته باشید، آن را در همان درخواست تنظیم کنید.</p>

<p>فرمت مقادیر مدت‌زمان مانند <code>500ms</code>، <code>2s</code> یا <code>1m</code> قابل استفاده است.</p>

<h3>نمونه‌های application.properties</h3>

<p>حداقل تنظیمات:</p>
<pre dir="ltr"><code class="language-properties">zarinpal.merchant-id=11111111-1111-1111-1111-111111111111
zarinpal.callback-url=https://example.com/payment/callback</code></pre>

<p>محیط تست:</p>
<pre dir="ltr"><code class="language-properties">zarinpal.merchant-id=11111111-1111-1111-1111-111111111111
zarinpal.callback-url=https://example.com/payment/callback
zarinpal.environment=SANDBOX</code></pre>

<p>تایم‌اوت‌های سفارشی:</p>
<pre dir="ltr"><code class="language-properties">zarinpal.merchant-id=11111111-1111-1111-1111-111111111111
zarinpal.callback-url=https://example.com/payment/callback
zarinpal.timeout.connect=5s
zarinpal.timeout.read=20s</code></pre>

<p>دامنه‌های سفارشی برای API و StartPay:</p>
<pre dir="ltr"><code class="language-properties">zarinpal.merchant-id=11111111-1111-1111-1111-111111111111
zarinpal.callback-url=https://example.com/payment/callback
zarinpal.base-url.production=https://api.example.com
zarinpal.base-url.sandbox=https://sandbox-api.example.com</code></pre>

<p>Retry و User-Agent سفارشی:</p>
<pre dir="ltr"><code class="language-properties">zarinpal.merchant-id=11111111-1111-1111-1111-111111111111
zarinpal.callback-url=https://example.com/payment/callback
zarinpal.retry.enabled=true
zarinpal.retry.max-attempts=3
zarinpal.retry.backoff=200ms
zarinpal.http.user-agent=MyApp-Zarinpal/1.0</code></pre>

<h2>آموزش استفاده</h2>

<h3>تزریق صفر بویلرپلیت</h3>

<pre dir="ltr"><code class="language-java">import com.ernoxin.zarinpaljavasdk.client.ZarinpalClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final ZarinpalClient client;
}
</code></pre>

<h3>گام ۱: ساخت درخواست پرداخت</h3>
<p>اگر <code>callback-url</code> را در تنظیمات تعریف کرده‌اید، مقدار <code>callbackUrl</code> را لازم نیست بدهید (یا در سازنده مستقیم <code>null</code> بگذارید) تا از مقدار تنظیمات استفاده شود.
<pre dir="ltr"><code class="language-java">import com.ernoxin.zarinpaljavasdk.client.ZarinpalClient;
import com.ernoxin.zarinpaljavasdk.model.PaymentRequest;
import com.ernoxin.zarinpaljavasdk.model.PaymentRequestResult;
import com.ernoxin.zarinpaljavasdk.model.ZarinpalCurrency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentFlow {
private final ZarinpalClient client;

    public PaymentRequestResult createRequest() {
        PaymentRequest request = PaymentRequest.builder(150000, "خرید اشتراک ماهانه")
                .currency(ZarinpalCurrency.IRR)
                .build();
        return client.requestPayment(request);
    }

}
</code></pre>
<p>مثال بدون Builder (استفاده از سازنده و مقدارهای نمونه برای یادگیری):</p>
<pre dir="ltr"><code class="language-java">import com.ernoxin.zarinpaljavasdk.client.ZarinpalClient;
import com.ernoxin.zarinpaljavasdk.model.PaymentRequest;
import com.ernoxin.zarinpaljavasdk.model.PaymentRequestResult;
import com.ernoxin.zarinpaljavasdk.model.ZarinpalCurrency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class PaymentFlow {
private final ZarinpalClient client;

    public PaymentRequestResult createRequest() {
        PaymentRequest request = new PaymentRequest(
                75000,
                "خرید اعتبار",
                URI.create("https://example.com/payment/callback"),
                ZarinpalCurrency.IRR,
                "A1B2C3",
                null,
                null,
                null
        );
        return client.requestPayment(request);
    }

}
</code></pre>

<h3>گام ۲: ساخت لینک ریدایرکت</h3>
<pre dir="ltr"><code class="language-java">import com.ernoxin.zarinpaljavasdk.client.ZarinpalClient;
import com.ernoxin.zarinpaljavasdk.model.PaymentRequestResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedirectFlow {
    private final ZarinpalClient client;

    public String buildRedirectUrl(PaymentRequestResult result) {
        return client.buildRedirectUrl(result.authority());
    }
}
</code></pre>

<h3>گام ۳: دریافت کال‌بک و اعتبارسنجی پارامترها</h3>
<pre dir="ltr"><code class="language-java">import com.ernoxin.zarinpaljavasdk.callback.ZarinpalCallback;
import com.ernoxin.zarinpaljavasdk.client.ZarinpalClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CallbackController {
    private final ZarinpalClient client;

    @GetMapping("/payment/callback")
    public String handleCallback(@RequestParam Map&lt;String, String&gt; params) {
        ZarinpalCallback callback = client.parseCallback(params);
        return callback.status().name();
    }
}
</code></pre>

<h3>گام ۴: وریفای پرداخت</h3>
<pre dir="ltr"><code class="language-java">import com.ernoxin.zarinpaljavasdk.callback.ZarinpalCallback;
import com.ernoxin.zarinpaljavasdk.client.ZarinpalClient;
import com.ernoxin.zarinpaljavasdk.model.VerifyRequest;
import com.ernoxin.zarinpaljavasdk.model.VerifyResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VerifyFlow {
    private final ZarinpalClient client;

    public VerifyResult verify(long amount, ZarinpalCallback callback) {
        if (!callback.isOk()) {
            return null;
        }
        return client.verifyPayment(new VerifyRequest(amount, callback.authority()));
    }
}
</code></pre>

<h2>مرجع متدهای ZarinpalClient</h2>

<p>همه متدهای کلاینت در این بخش آمده‌اند. هر مثال مستقل است و فرض می‌کند <code>ZarinpalClient</code> تزریق شده و مقادیر لازم مثل <code>amount</code> یا <code>authority</code> از قبل آماده‌اند.</p>

<h3><code>requestPayment</code></h3>
<p>ساخت درخواست پرداخت و دریافت <code>authority</code> برای ریدایرکت.</p>
<pre dir="ltr"><code class="language-java">PaymentRequest request = PaymentRequest.builder(10000, "خرید اشتراک")
        .currency(ZarinpalCurrency.IRR)
        .build();

PaymentRequestResult result = client.requestPayment(request);
String authority = result.authority();
</code></pre>

<h3><code>buildRedirectUrl</code></h3>
<p>ساخت لینک StartPay برای ریدایرکت کاربر.</p>
<pre dir="ltr"><code class="language-java">String redirectUrl = client.buildRedirectUrl(authority);
</code></pre>

<h3><code>parseCallback</code></h3>
<p>استخراج و اعتبارسنجی پارامترهای کال‌بک (Authority/Status) از Map.</p>
<pre dir="ltr"><code class="language-java">ZarinpalCallback callback = client.parseCallback(params);
</code></pre>
<p>اگر در کنترلر Spring از <code>MultiValueMap</code> استفاده می‌کنید:</p>
<pre dir="ltr"><code class="language-java">ZarinpalCallback callback = client.parseCallback(queryParams);
</code></pre>

<h3><code>verifyPayment</code></h3>
<p>در صورت OK بودن وضعیت کال‌بک، تراکنش را وریفای می‌کند.</p>
<pre dir="ltr"><code class="language-java">ZarinpalCallback callback = client.parseCallback(params);
if (callback.isOk()) {
    VerifyResult verify = client.verifyPayment(new VerifyRequest(amount, callback.authority()));
}
</code></pre>

<h3><code>reversePayment</code></h3>
<p>ریورس تراکنش موفق با <code>authority</code>.</p>
<pre dir="ltr"><code class="language-java">ReverseResult reverse = client.reversePayment(new ReverseRequest(authority));
</code></pre>

<h3><code>unverifiedPayments</code></h3>
<p>لیست تراکنش‌های وریفای‌نشده را برمی‌گرداند.</p>
<pre dir="ltr"><code class="language-java">UnverifiedResult unverified = client.unverifiedPayments();
unverified.authorities().forEach(item -&gt; {
    String unverifiedAuthority = item.authority();
});
</code></pre>

<h3><code>inquirePayment</code></h3>
<p>استعلام وضعیت تراکنش با <code>authority</code>.</p>
<pre dir="ltr"><code class="language-java">InquiryResult inquiry = client.inquirePayment(new InquiryRequest(authority));
</code></pre>

<h3><code>calculateFee</code></h3>
<p>محاسبه کارمزد بر اساس مبلغ و ارز.</p>
<pre dir="ltr"><code class="language-java">FeeCalculationResult fee = client.calculateFee(
        new FeeCalculationRequest(10000, ZarinpalCurrency.IRR)
);
</code></pre>

<h2>مدیریت خطاها</h2>

<ul>
  <li><b>ZarinpalException</b> کلاس پایه برای تمام خطاهای SDK و مناسب برای گرفتن همه استثناها به‌صورت کلی</li>
  <li><b>ZarinpalValidationException</b> خطاهای اعتبارسنجی سمت کلاینت یا پیکربندی نامعتبر</li>
  <li><b>ZarinpalTransportException</b> خطاهای شبکه، تایم‌اوت یا قطعی ارتباط</li>
  <li><b>ZarinpalApiException</b> خطای پاسخ از درگاه یا کدهای وضعیت ناموفق؛ شامل کد HTTP، کد درگاه و پیام</li>
  <li><b>ZarinpalCallbackException</b> پارامترهای ناقص یا نامعتبر در کال‌بک</li>
</ul>

<h3>جدول کدهای خطا</h3>

<table>
  <thead>
    <tr>
      <th>کد</th>
      <th>توضیح</th>
    </tr>
  </thead>
  <tbody>
    <tr><td dir="ltr">-63</td><td>مهلت ریورس این سشن منقضی شده است</td></tr>
    <tr><td dir="ltr">-62</td><td>محدودیت IP ترمینال باید فعال باشد</td></tr>
    <tr><td dir="ltr">-61</td><td>سشن در وضعیت موفق نیست</td></tr>
    <tr><td dir="ltr">-60</td><td>امکان ریورس با بانک وجود ندارد</td></tr>
    <tr><td dir="ltr">-55</td><td>درخواست پرداخت دستی یافت نشد</td></tr>
    <tr><td dir="ltr">-54</td><td>آتوریتی نامعتبر است</td></tr>
    <tr><td dir="ltr">-53</td><td>سشن متعلق به این پذیرنده نیست</td></tr>
    <tr><td dir="ltr">-52</td><td>خطای غیرمنتظره، با پشتیبانی تماس بگیرید</td></tr>
    <tr><td dir="ltr">-51</td><td>پرداخت ناموفق است یا سشن غیرفعال است</td></tr>
    <tr><td dir="ltr">-50</td><td>مبلغ پرداخت‌شده با مبلغ ارسال‌شده تطابق ندارد</td></tr>
    <tr><td dir="ltr">-41</td><td>حداکثر مبلغ ۱۰۰٬۰۰۰٬۰۰۰ تومان است</td></tr>
    <tr><td dir="ltr">-40</td><td>پارامترهای اضافی نامعتبر است</td></tr>
    <tr><td dir="ltr">-39</td><td>خطا در تسهیم</td></tr>
    <tr><td dir="ltr">-38</td><td>شبا در شاپرک ثبت نشده است</td></tr>
    <tr><td dir="ltr">-37</td><td>یک یا چند شماره شبا برای تسهیم غیرفعال است</td></tr>
    <tr><td dir="ltr">-36</td><td>حداقل مبلغ تسهیم شناور ۱۰٬۰۰۰ ریال است</td></tr>
    <tr><td dir="ltr">-35</td><td>تعداد بخش‌های تسهیم شناور از حد مجاز بیشتر است</td></tr>
    <tr><td dir="ltr">-34</td><td>مجموع مبالغ تسهیم ثابت از حد مجاز بیشتر است</td></tr>
    <tr><td dir="ltr">-33</td><td>تسهیم شناور نامعتبر است</td></tr>
    <tr><td dir="ltr">-32</td><td>مجموع مبالغ تسهیم شناور از حد مجاز بیشتر است</td></tr>
    <tr><td dir="ltr">-31</td><td>حساب بانکی پیش‌فرض برای تسویه تعریف نشده است</td></tr>
    <tr><td dir="ltr">-30</td><td>پذیرنده اجازه تسویه اشتراکی شناور ندارد</td></tr>
    <tr><td dir="ltr">-19</td><td>تراکنش‌های پذیرنده مسدود شده است</td></tr>
    <tr><td dir="ltr">-18</td><td>دامنه معرف با دامنه ثبت‌شده مطابقت ندارد</td></tr>
    <tr><td dir="ltr">-17</td><td>سطح پذیرنده معتبر نیست</td></tr>
    <tr><td dir="ltr">-16</td><td>سطح پذیرنده معتبر نیست</td></tr>
    <tr><td dir="ltr">-15</td><td>حساب پذیرنده تعلیق شده است</td></tr>
    <tr><td dir="ltr">-14</td><td>دامنه کال‌بک با دامنه ثبت‌شده مطابقت ندارد</td></tr>
    <tr><td dir="ltr">-13</td><td>سقف مجاز ترمینال تکمیل شده است</td></tr>
    <tr><td dir="ltr">-12</td><td>تعداد تلاش‌ها بیش از حد مجاز است</td></tr>
    <tr><td dir="ltr">-11</td><td>ترمینال غیرفعال است</td></tr>
    <tr><td dir="ltr">-10</td><td>ترمینال معتبر نیست یا IP اشتباه است</td></tr>
    <tr><td dir="ltr">-9</td><td>خطای اعتبارسنجی</td></tr>
    <tr><td dir="ltr">-5</td><td>کد معرف نامعتبر است</td></tr>
    <tr><td dir="ltr">-4</td><td>مبلغ نامعتبر است</td></tr>
    <tr><td dir="ltr">-3</td><td>توضیحات نامعتبر یا بیش از حد مجاز است</td></tr>
    <tr><td dir="ltr">-2</td><td>آدرس بازگشت الزامی است</td></tr>
    <tr><td dir="ltr">100</td><td>عملیات موفق</td></tr>
    <tr><td dir="ltr">101</td><td>تراکنش قبلا وریفای شده است</td></tr>
    <tr><td dir="ltr">429</td><td>فرمت کد معرف نامعتبر است</td></tr>
  </tbody>
</table>

<h2>امنیت و بهترین‌عمل‌ها</h2>

<ul>
  <li>پارامترهای کال‌بک را با <code>parseCallback</code> اعتبارسنجی کنید و فقط در صورت OK بودن وضعیت، وریفای انجام دهید</li>
  <li>از وریفای دوباره یک آتوریتی جلوگیری کنید و وضعیت تراکنش را در دیتابیس ذخیره کنید</li>
  <li>حتما از HTTPS برای کال‌بک و ارتباطات حساس استفاده کنید</li>
  <li>در صورت فعال‌سازی لاگ، از ثبت merchant-id، شماره کارت یا اطلاعات حساس خودداری کنید</li>
  <li>برای محیط تست از SANDBOX استفاده کنید و مرچنت کد تستی تنظیم کنید</li>
</ul>

<h2>مشارکت‌کنندگان</h2>

<ul>
  <li>عرفان داوودی نصر — <a href="mailto:davoodinasr.erfan@gamil.com">davoodinasr.erfan@gamil.com</a></li>
</ul>

<p>اگر این SDK برای شما مفید بوده، لطفا به ریپو ستاره بدهید و برای حمایت بیشتر، من را در گیت‌هاب دنبال کنید.</p>

<h2>پرسش‌های متداول</h2>

<h3>چرا برنامه در استارت خطا می‌دهد؟</h3>
<p>دو مقدار <code>zarinpal.merchant-id</code> و <code>zarinpal.callback-url</code> اجباری هستند. اگر خالی یا نامعتبر باشند، برنامه در استارت متوقف می‌شود.</p>

<h3>چرا لینک ریدایرکت به دامنه اشتباه می‌رود؟</h3>
<p>مقدار <code>zarinpal.base-url.production</code> یا <code>zarinpal.base-url.sandbox</code> را بررسی کنید و با محیط اجرای فعلی تطبیق دهید.</p>

<h3>کد 101 در وریفای یعنی چه؟</h3>
<p>تراکنش قبلا وریفای شده است. این وضعیت را به‌عنوان موفقیت مدیریت کنید و عملیات را تکرار نکنید.</p>

<h3>چرا خطای 14- دریافت می‌شود؟</h3>
<p>دامنه کال‌بک با دامنه ثبت‌شده در پنل زرین‌پال مطابقت ندارد. دامنه و پروتکل را بررسی کنید.</p>

</div>
