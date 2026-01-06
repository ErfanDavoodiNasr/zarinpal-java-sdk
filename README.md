<div dir="rtl">

<h1><span dir="ltr">SDK</span> جاوا زرین‌پال</h1>

<p>این <span dir="ltr">SDK</span> برای تیم‌هایی طراحی شده است که می‌خواهند اتصال به درگاه زرین‌پال را در یک پروژه <span dir="ltr">Spring Boot</span> با کمترین بویلرپلیت انجام دهند. فلسفه طراحی آن <b><span dir="ltr">Configuration-first</span></b> است: تمام تنظیمات از طریق <code dir="ltr">application.properties</code> یا <code dir="ltr">application.yml</code> انجام می‌شود، و اگر مقادیر ناقص باشند، برنامه در لحظه استارت به‌صورت <b><span dir="ltr">fail-fast</span></b> متوقف می‌شود تا خطا به مرحله درخواست پرداخت نرسد.</p>

<p>پس از تنظیم <span dir="ltr">properties</span>، کافی است <code dir="ltr">ZarinpalClient</code> را تزریق کنید و از آن استفاده کنید.</p>

<h2>پیش‌نیازها</h2>
<ul>
  <li><span dir="ltr">Java 21</span></li>
  <li><span dir="ltr">Spring Boot 4.0.1</span></li>
</ul>

<h2>نصب بدون انتشار در <span dir="ltr">Maven Central</span></h2>

<h3>مرحله ۱: نصب محلی <span dir="ltr">SDK</span></h3>
<div dir="ltr">
<pre dir="ltr"><code dir="ltr" class="language-bash">mvn clean install</code></pre>
</div>

<h3>مرحله ۲: استفاده در پروژه مصرف‌کننده</h3>
<div dir="ltr">
<pre dir="ltr"><code dir="ltr" class="language-xml">&lt;dependency&gt;
  &lt;groupId&gt;com.ernoxin&lt;/groupId&gt;
  &lt;artifactId&gt;ZarinPal-java-SDK&lt;/artifactId&gt;
  &lt;version&gt;1.0.0&lt;/version&gt;
&lt;/dependency&gt;
</code></pre>
</div>

<h2>پیکربندی (<span dir="ltr">Configuration-first</span>)</h2>

<p>دو مقدار اجباری هستند و در صورت عدم وجود یا خالی بودن باعث خطای استارت می‌شوند: <code dir="ltr">zarinpal.merchant-id</code> و <code dir="ltr">zarinpal.callback-url</code>. سایر گزینه‌ها اختیاری‌اند و دارای مقدار پیش‌فرض هستند.</p>

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
    <tr><td><code dir="ltr">zarinpal.merchant-id</code></td><td>بله</td><td>-</td><td>شناسه پذیرنده (<span dir="ltr">UUID</span>)</td></tr>
    <tr><td><code dir="ltr">zarinpal.callback-url</code></td><td>بله</td><td>-</td><td>آدرس بازگشت درگاه با <code dir="ltr">http/https</code></td></tr>
    <tr><td><code dir="ltr">zarinpal.environment</code></td><td>خیر</td><td><code dir="ltr">PRODUCTION</code></td><td>محیط اجرا: <code dir="ltr">PRODUCTION</code> یا <code dir="ltr">SANDBOX</code></td></tr>
    <tr><td><code dir="ltr">zarinpal.base-url.production</code></td><td>خیر</td><td><code dir="ltr">https://payment.zarinpal.com</code></td><td>دامنه <span dir="ltr">API</span> و <span dir="ltr">StartPay</span> در محیط عملیاتی</td></tr>
    <tr><td><code dir="ltr">zarinpal.base-url.sandbox</code></td><td>خیر</td><td><code dir="ltr">https://sandbox.zarinpal.com</code></td><td>دامنه <span dir="ltr">API</span> و <span dir="ltr">StartPay</span> در محیط تست</td></tr>
    <tr><td><code dir="ltr">zarinpal.operation-version</code></td><td>خیر</td><td><code dir="ltr">v4</code></td><td>نسخه مسیر عملیات <span dir="ltr">API</span></td></tr>
    <tr><td><code dir="ltr">zarinpal.timeout.connect</code></td><td>خیر</td><td><code dir="ltr">10s</code></td><td>مهلت اتصال</td></tr>
    <tr><td><code dir="ltr">zarinpal.timeout.read</code></td><td>خیر</td><td><code dir="ltr">30s</code></td><td>مهلت خواندن پاسخ</td></tr>
    <tr><td><code dir="ltr">zarinpal.retry.enabled</code></td><td>خیر</td><td><code dir="ltr">false</code></td><td>فعال‌سازی تلاش مجدد در خطاهای شبکه</td></tr>
    <tr><td><code dir="ltr">zarinpal.retry.max-attempts</code></td><td>خیر</td><td><code dir="ltr">1</code></td><td>تعداد تلاش‌ها در صورت فعال بودن <code dir="ltr">retry</code></td></tr>
    <tr><td><code dir="ltr">zarinpal.retry.backoff</code></td><td>خیر</td><td><code dir="ltr">0ms</code></td><td>وقفه بین تلاش‌ها</td></tr>
    <tr><td><code dir="ltr">zarinpal.http.user-agent</code></td><td>خیر</td><td><code dir="ltr">ZarinpalJavaSdk</code></td><td><code dir="ltr">User-Agent</code> ارسال‌شده به درگاه</td></tr>
  </tbody>
</table>

<p><b>نکته:</b> مقادیری مثل <code dir="ltr">merchant-id</code>، <code dir="ltr">callback-url</code> و <code dir="ltr">environment</code> فقط از فایل تنظیمات خوانده می‌شوند و نیاز نیست در متدها پاس داده شوند. فقط اگر می‌خواهید برای یک درخواست خاص <code dir="ltr">callbackUrl</code> متفاوتی داشته باشید، آن را در همان درخواست تنظیم کنید.</p>

<p>فرمت مقادیر مدت‌زمان مانند <code dir="ltr">500ms</code>، <code dir="ltr">2s</code> یا <code dir="ltr">1m</code> قابل استفاده است.</p>

<h3>نمونه‌های <span dir="ltr">application.properties</span></h3>

<p>حداقل تنظیمات:</p>
<div dir="ltr">
<pre dir="ltr"><code dir="ltr" class="language-properties">zarinpal.merchant-id=11111111-1111-1111-1111-111111111111
zarinpal.callback-url=https://example.com/payment/callback</code></pre>
</div>

<p>محیط تست:</p>
<div dir="ltr">
<pre dir="ltr"><code dir="ltr" class="language-properties">zarinpal.merchant-id=11111111-1111-1111-1111-111111111111
zarinpal.callback-url=https://example.com/payment/callback
zarinpal.environment=SANDBOX</code></pre>
</div>

<p>تایم‌اوت‌های سفارشی:</p>
<div dir="ltr">
<pre dir="ltr"><code dir="ltr" class="language-properties">zarinpal.merchant-id=11111111-1111-1111-1111-111111111111
zarinpal.callback-url=https://example.com/payment/callback
zarinpal.timeout.connect=5s
zarinpal.timeout.read=20s</code></pre>
</div>

<p>دامنه‌های سفارشی برای <span dir="ltr">API</span> و <span dir="ltr">StartPay</span>:</p>
<div dir="ltr">
<pre dir="ltr"><code dir="ltr" class="language-properties">zarinpal.merchant-id=11111111-1111-1111-1111-111111111111
zarinpal.callback-url=https://example.com/payment/callback
zarinpal.base-url.production=https://api.example.com
zarinpal.base-url.sandbox=https://sandbox-api.example.com</code></pre>
</div>

<p><span dir="ltr">Retry</span> و <span dir="ltr">User-Agent</span> سفارشی:</p>
<div dir="ltr">
<pre dir="ltr"><code dir="ltr" class="language-properties">zarinpal.merchant-id=11111111-1111-1111-1111-111111111111
zarinpal.callback-url=https://example.com/payment/callback
zarinpal.retry.enabled=true
zarinpal.retry.max-attempts=3
zarinpal.retry.backoff=200ms
zarinpal.http.user-agent=MyApp-Zarinpal/1.0</code></pre>
</div>

<h2>آموزش استفاده</h2>

<h3>تزریق صفر بویلرپلیت</h3>

<div dir="ltr">
<pre dir="ltr"><code dir="ltr" class="language-java">import com.ernoxin.zarinpaljavasdk.client.ZarinpalClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
private final ZarinpalClient client;
}
</code></pre>
</div>

<h3>گام ۱: ساخت درخواست پرداخت</h3>
<p>اگر <code dir="ltr">callback-url</code> را در تنظیمات تعریف کرده‌اید، مقدار <code dir="ltr">callbackUrl</code> را لازم نیست بدهید (یا در سازنده مستقیم <code dir="ltr">null</code> بگذارید) تا از مقدار تنظیمات استفاده شود.
<div dir="ltr">
<pre dir="ltr"><code dir="ltr" class="language-java">import com.ernoxin.zarinpaljavasdk.client.ZarinpalClient;
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
</div>
<p>مثال بدون Builder (استفاده از سازنده و مقدارهای نمونه برای یادگیری):</p>
<div dir="ltr">
<pre dir="ltr"><code dir="ltr" class="language-java">import com.ernoxin.zarinpaljavasdk.client.ZarinpalClient;
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
</div>

<h3>گام ۲: ساخت لینک ریدایرکت</h3>
<div dir="ltr">
<pre dir="ltr"><code dir="ltr" class="language-java">import com.ernoxin.zarinpaljavasdk.client.ZarinpalClient;
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
</div>

<h3>گام ۳: دریافت کال‌بک و اعتبارسنجی پارامترها</h3>
<div dir="ltr">
<pre dir="ltr"><code dir="ltr" class="language-java">import com.ernoxin.zarinpaljavasdk.callback.ZarinpalCallback;
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
</div>

<h3>گام ۴: وریفای پرداخت</h3>
<div dir="ltr">
<pre dir="ltr"><code dir="ltr" class="language-java">import com.ernoxin.zarinpaljavasdk.callback.ZarinpalCallback;
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
</div>

<h2>مرجع متدهای ZarinpalClient</h2>

<p>همه متدهای کلاینت در این بخش آمده‌اند. هر مثال مستقل است و فرض می‌کند <code dir="ltr">ZarinpalClient</code> تزریق شده و مقادیر لازم مثل <code dir="ltr">amount</code> یا <code dir="ltr">authority</code> از قبل آماده‌اند.</p>

<h3><code dir="ltr">requestPayment</code></h3>
<p>ساخت درخواست پرداخت و دریافت <code dir="ltr">authority</code> برای ریدایرکت.</p>
<div dir="ltr">
<pre dir="ltr"><code dir="ltr" class="language-java">PaymentRequest request = PaymentRequest.builder(10000, "خرید اشتراک")
        .currency(ZarinpalCurrency.IRR)
        .build();

PaymentRequestResult result = client.requestPayment(request);
String authority = result.authority();
</code></pre>
</div>

<h3><code dir="ltr">buildRedirectUrl</code></h3>
<p>ساخت لینک <span dir="ltr">StartPay</span> برای ریدایرکت کاربر.</p>
<div dir="ltr">
<pre dir="ltr"><code dir="ltr" class="language-java">String redirectUrl = client.buildRedirectUrl(authority);
</code></pre>
</div>

<h3><code dir="ltr">parseCallback</code></h3>
<p>استخراج و اعتبارسنجی پارامترهای کال‌بک (<code dir="ltr">Authority/Status</code>) از <span dir="ltr">Map</span>.</p>
<div dir="ltr">
<pre dir="ltr"><code dir="ltr" class="language-java">ZarinpalCallback callback = client.parseCallback(params);
</code></pre>
</div>
<p>اگر در کنترلر <span dir="ltr">Spring</span> از <code dir="ltr">MultiValueMap</code> استفاده می‌کنید:</p>
<div dir="ltr">
<pre dir="ltr"><code dir="ltr" class="language-java">ZarinpalCallback callback = client.parseCallback(queryParams);
</code></pre>
</div>

<h3><code dir="ltr">verifyPayment</code></h3>
<p>در صورت <code dir="ltr">OK</code> بودن وضعیت کال‌بک، تراکنش را وریفای می‌کند.</p>
<div dir="ltr">
<pre dir="ltr"><code dir="ltr" class="language-java">ZarinpalCallback callback = client.parseCallback(params);
if (callback.isOk()) {
    VerifyResult verify = client.verifyPayment(new VerifyRequest(amount, callback.authority()));
}
</code></pre>
</div>

<h3><code dir="ltr">reversePayment</code></h3>
<p>ریورس تراکنش موفق با <code dir="ltr">authority</code>.</p>
<div dir="ltr">
<pre dir="ltr"><code dir="ltr" class="language-java">ReverseResult reverse = client.reversePayment(new ReverseRequest(authority));
</code></pre>
</div>

<h3><code dir="ltr">unverifiedPayments</code></h3>
<p>لیست تراکنش‌های وریفای‌نشده را برمی‌گرداند.</p>
<div dir="ltr">
<pre dir="ltr"><code dir="ltr" class="language-java">UnverifiedResult unverified = client.unverifiedPayments();
unverified.authorities().forEach(item -&gt; {
    String unverifiedAuthority = item.authority();
});
</code></pre>
</div>

<h3><code dir="ltr">inquirePayment</code></h3>
<p>استعلام وضعیت تراکنش با <code dir="ltr">authority</code>.</p>
<div dir="ltr">
<pre dir="ltr"><code dir="ltr" class="language-java">InquiryResult inquiry = client.inquirePayment(new InquiryRequest(authority));
</code></pre>
</div>

<h3><code dir="ltr">calculateFee</code></h3>
<p>محاسبه کارمزد بر اساس مبلغ و ارز.</p>
<div dir="ltr">
<pre dir="ltr"><code dir="ltr" class="language-java">FeeCalculationResult fee = client.calculateFee(
        new FeeCalculationRequest(10000, ZarinpalCurrency.IRR)
);
</code></pre>
</div>

<h2>مدیریت خطاها</h2>

<ul>
  <li><b><span dir="ltr">ZarinpalException</span></b> کلاس پایه برای تمام خطاهای <span dir="ltr">SDK</span> و مناسب برای گرفتن همه استثناها به‌صورت کلی</li>
  <li><b><span dir="ltr">ZarinpalValidationException</span></b> خطاهای اعتبارسنجی سمت کلاینت یا پیکربندی نامعتبر</li>
  <li><b><span dir="ltr">ZarinpalTransportException</span></b> خطاهای شبکه، تایم‌اوت یا قطعی ارتباط</li>
  <li><b><span dir="ltr">ZarinpalApiException</span></b> خطای پاسخ از درگاه یا کدهای وضعیت ناموفق؛ شامل کد <code dir="ltr">HTTP</code>، کد درگاه و پیام</li>
  <li><b><span dir="ltr">ZarinpalCallbackException</span></b> پارامترهای ناقص یا نامعتبر در کال‌بک</li>
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
    <tr><td dir="ltr">-62</td><td>محدودیت <span dir="ltr">IP</span> ترمینال باید فعال باشد</td></tr>
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
    <tr><td dir="ltr">-10</td><td>ترمینال معتبر نیست یا <span dir="ltr">IP</span> اشتباه است</td></tr>
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
  <li>پارامترهای کال‌بک را با <code dir="ltr">parseCallback</code> اعتبارسنجی کنید و فقط در صورت <code dir="ltr">OK</code> بودن وضعیت، وریفای انجام دهید</li>
  <li>از وریفای دوباره یک آتوریتی جلوگیری کنید و وضعیت تراکنش را در دیتابیس ذخیره کنید</li>
  <li>حتما از <code dir="ltr">HTTPS</code> برای کال‌بک و ارتباطات حساس استفاده کنید</li>
  <li>در صورت فعال‌سازی لاگ، از ثبت <code dir="ltr">merchant-id</code>، شماره کارت یا اطلاعات حساس خودداری کنید</li>
  <li>برای محیط تست از <code dir="ltr">SANDBOX</code> استفاده کنید و مرچنت کد تستی تنظیم کنید</li>
</ul>

<h2>مشارکت‌کنندگان</h2>

<ul>
  <li>عرفان داوودی نصر — <a href="mailto:davoodinasr.erfan@gamil.com">davoodinasr.erfan@gamil.com</a></li>
</ul>

<p>اگر این <span dir="ltr">SDK</span> برای شما مفید بوده، لطفا به ریپو ستاره بدهید و برای حمایت بیشتر، من را در گیت‌هاب دنبال کنید.</p>

<h2>پرسش‌های متداول</h2>

<h3>چرا برنامه در استارت خطا می‌دهد؟</h3>
<p>دو مقدار <code dir="ltr">zarinpal.merchant-id</code> و <code dir="ltr">zarinpal.callback-url</code> اجباری هستند. اگر خالی یا نامعتبر باشند، برنامه در استارت متوقف می‌شود.</p>

<h3>چرا لینک ریدایرکت به دامنه اشتباه می‌رود؟</h3>
<p>مقدار <code dir="ltr">zarinpal.base-url.production</code> یا <code dir="ltr">zarinpal.base-url.sandbox</code> را بررسی کنید و با محیط اجرای فعلی تطبیق دهید.</p>

<h3>کد 101 در وریفای یعنی چه؟</h3>
<p>تراکنش قبلا وریفای شده است. این وضعیت را به‌عنوان موفقیت مدیریت کنید و عملیات را تکرار نکنید.</p>

<h3>چرا خطای 14- دریافت می‌شود؟</h3>
<p>دامنه کال‌بک با دامنه ثبت‌شده در پنل زرین‌پال مطابقت ندارد. دامنه و پروتکل را بررسی کنید.</p>

</div>
